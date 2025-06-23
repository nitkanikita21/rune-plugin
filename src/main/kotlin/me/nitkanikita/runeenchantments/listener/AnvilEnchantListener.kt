package me.nitkanikita.runeenchantments.listener

import me.nitkanikita.runeenchantments.config.ConfigLoader
import me.nitkanikita.runeenchantments.util.EnchantUtils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.AnvilInventory

class AnvilEnchantListener(
    private val configLoader: ConfigLoader
) : Listener {

    @EventHandler
    fun onPrepareAnvil(event: PrepareAnvilEvent) {
        val inventory = event.inventory as? AnvilInventory ?: return
        val left = inventory.firstItem ?: return
        val right = inventory.secondItem ?: return

        if (left.type == Material.AIR || right.type == Material.AIR) return
        if (!right.type.name.contains("BOOK")) return

        if (!EnchantUtils.isEnchant(right)) return

        if (!left.type.name.endsWith("_PICKAXE")) return

        val result = left.clone()
        EnchantUtils.enchantItem(result, configLoader.config)

        event.result = result
        inventory.repairCost = 5
    }
}