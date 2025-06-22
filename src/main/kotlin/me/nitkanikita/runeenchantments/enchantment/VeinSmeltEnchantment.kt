package me.nitkanikita.runeenchantments.enchantment

import io.papermc.paper.enchantments.EnchantmentRarity
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.entity.EntityCategory
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

class VeinSmeltEnchantment(private val component: Component) : Enchantment() {
    override fun getKey(): NamespacedKey = NamespacedKey.minecraft("veinsmelt")

    override fun getTranslationKey(): String = "enchantment.rune.veinsmelt"

    override fun getName(): String = "Rune: VeinSmelt"
    override fun getMaxLevel(): Int = 1
    override fun getStartLevel(): Int = 1
    override fun getItemTarget(): EnchantmentTarget = EnchantmentTarget.TOOL
    override fun canEnchantItem(item: ItemStack): Boolean {
        return item.type.name.endsWith("_PICKAXE")
    }
    override fun conflictsWith(other: Enchantment): Boolean = false
    override fun isTreasure(): Boolean = true
    override fun isCursed(): Boolean = false
    override fun isTradeable(): Boolean = false
    override fun isDiscoverable(): Boolean = false
    override fun translationKey(): String = "enchantment.rune.veinsmelt"
    override fun displayName(level: Int): Component = component
    override fun getMinModifiedCost(level: Int): Int = 15
    override fun getMaxModifiedCost(level: Int): Int = 40
    override fun getRarity(): EnchantmentRarity = EnchantmentRarity.VERY_RARE
    override fun getDamageIncrease(level: Int, entityCategory: EntityCategory): Float = 0f

    override fun getActiveSlots(): MutableSet<EquipmentSlot> = mutableSetOf(
        EquipmentSlot.HAND, EquipmentSlot.OFF_HAND
    )
}
