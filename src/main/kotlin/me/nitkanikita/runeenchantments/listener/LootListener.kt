package me.nitkanikita.runeenchantments.listener

import me.nitkanikita.runeenchantments.config.ConfigLoader
import me.nitkanikita.runeenchantments.util.EnchantUtils
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.LootGenerateEvent
import org.bukkit.inventory.ItemStack
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
        book.editMeta { meta ->
            meta.lore(config.lore.bakeAsLore)
        }
        EnchantUtils.enchantItem(book, config)

        event.loot.add(book)
    }
}