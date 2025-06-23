package me.nitkanikita.runeenchantments.util

import me.nitkanikita.runeenchantments.config.PluginConfig
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object EnchantUtils {
    val VEIN_SMELT_KEY = NamespacedKey("rune", "vein_smelt")


    fun isEnchant(item: ItemStack): Boolean {
        return item.itemMeta?.persistentDataContainer?.has(VEIN_SMELT_KEY, PersistentDataType.BYTE) ?: false
    }

    fun enchantItem(item: ItemStack, config: PluginConfig) {
        item.editMeta {
            it.persistentDataContainer?.set(VEIN_SMELT_KEY, PersistentDataType.BYTE, 1)
            it.lore((it.lore() ?: listOf()) + config.enchantmentLine.bake)
        }
    }
}