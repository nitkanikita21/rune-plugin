package me.nitkanikita.runeenchantments.config

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Setting

@ConfigSerializable
class RawComponent(
    @Setting(nodeFromParent = true)
    private var _raw: List<String> = listOf()
) {

    constructor(vararg rawArg: String) : this(rawArg.toList())

    val raw: List<String> get() = _raw

    @delegate:Transient
    val bake: Component by lazy {
        var message: Component = Component.empty()
        _raw.forEachIndexed { i, line ->
            message = message.append(MINI_MESSAGE.deserialize(line))
            if (i + 1 < _raw.size) message = message.append(Component.newline())
        }
        message
    }

    @delegate:Transient
    val bakeAsLore: List<Component> by lazy {
        raw.map { MiniMessage.miniMessage().deserialize(it) }
    }

    fun <T : Number> pluralized(number: T): RawComponent {
        require(_raw.size == 3) { "The component must have contain exactly 3 forms: singular, few, and many." }

        val n = number.toLong()
        return when {
            n % 100 in 11..19 -> RawComponent(_raw[2])
            n % 10 == 1L -> RawComponent(_raw[0])
            n % 10 in 2..4 -> RawComponent(_raw[1])
            else -> RawComponent(_raw[2])
        }
    }

    fun replaceText(placeholder: String, text: String): RawComponent {
        return RawComponent(
            _raw.map { messageLine: String ->
                messageLine.replace(
                    placeholder, text
                )
            }.toList()
        )
    }

    fun replaceText(placeholder: String, text: Component): RawComponent {
        return replaceText(placeholder, MINI_MESSAGE.serialize(text))
    }

    fun replaceText(placeholder: String, text: RawComponent): RawComponent {
        return replaceText(placeholder, text.bake)
    }

    fun replaceText(placeholder: String, obj: Any): RawComponent {
        return replaceText(placeholder, obj.toString())
    }

    fun send(audience: Audience) {
        audience.sendMessage(bake)
    }

    fun send() {
        val msg: Component = bake;
        Bukkit.getOnlinePlayers().forEach { it.sendMessage(msg) }
        Bukkit.getConsoleSender().sendMessage(msg)
    }

    fun <T : Audience> send(listOfAudience: List<T>) {
        val msg: Component = bake;
        listOfAudience.forEach { it.sendMessage(msg) }
    }

    companion object {
        private val MINI_MESSAGE: MiniMessage = MiniMessage.miniMessage()
    }

}

