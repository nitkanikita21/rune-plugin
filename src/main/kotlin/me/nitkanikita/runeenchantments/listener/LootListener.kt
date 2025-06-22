package me.nitkanikita.runeenchantments.listener

import me.nitkanikita.runeenchantments.config.ConfigLoader
import me.nitkanikita.runeenchantments.config.PluginConfig
import me.nitkanikita.runeenchantments.enchantment.VeinSmeltEnchantment
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.LootGenerateEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import kotlin.random.Random

class LootListener(private val configLoader: ConfigLoader) : Listener {

    private val targetLootTables = setOf(
        NamespacedKey.minecraft("chests/simple_dungeon"),
        NamespacedKey.minecraft("chests/nether_bridge")
    )

    @EventHandler
    fun onLootGenerate(event: LootGenerateEvent) {
        val config = configLoader.config

        val table = event.lootTable.key
        if (table !in targetLootTables) return
        if (Random.nextDouble() >= config.chance) return

        val book = ItemStack(Material.ENCHANTED_BOOK)
        val meta = book.itemMeta as EnchantmentStorageMeta
        meta.addStoredEnchant(VeinSmeltEnchantment(config.name.bake), 1, true)
        meta.lore(config.lore.bakeAsLore)
        book.itemMeta = meta

        event.loot.add(book)
    }
}