package me.nitkanikita.runeenchantments.command

import me.nitkanikita.runeenchantments.config.ConfigLoader
import me.nitkanikita.runeenchantments.util.EnchantUtils
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.incendo.cloud.CommandManager
import org.incendo.cloud.bukkit.parser.PlayerParser

class Commands(
    configLoader: ConfigLoader,
    commandManager: CommandManager<CommandSender>,
) {
    init {
        val root = commandManager.commandBuilder("rune")
            .permission("runeenchantments.command")

        root.literal("reload")
            .permission("runeenchantments.command.reload")
            .handler { ctx ->
                configLoader.load()
                ctx.sender().sendMessage("Config reloaded")
            }
            .let(commandManager::command)


        root.literal("give")
            .permission("runeenchantments.command.reload")
            .optional("player", PlayerParser.playerParser())
            .handler { ctx ->
                val config = configLoader.config

                val target: Player = ctx.optional<Player>("player").orElse(ctx.sender() as Player)

                val book = ItemStack(Material.ENCHANTED_BOOK)
                book.editMeta { meta ->
                    meta.lore(config.lore.bakeAsLore)
                    meta.displayName(config.name.bake)
                }
                EnchantUtils.enchantItem(book, config)

                target.inventory.addItem(book)
            }
            .let(commandManager::command)
    }
}