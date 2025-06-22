package me.nitkanikita.runeenchantments.listener

import me.nitkanikita.runeenchantments.config.ConfigLoader
import me.nitkanikita.runeenchantments.config.PluginConfig
import me.nitkanikita.runeenchantments.enchantment.VeinSmeltEnchantment
import me.nitkanikita.runeenchantments.util.VeinFinder
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

class BlockBreakListener(private val configLoader: ConfigLoader) : Listener {
    private val ores = setOf(
        Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE,
        Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
        Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE,
        Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
        Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE,
        Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE,
        Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE,
        Material.NETHER_QUARTZ_ORE
    )

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        val config = configLoader.config

        val tool = e.player.inventory.itemInMainHand ?: return
        if (!tool.hasItemMeta() || !tool.itemMeta.hasEnchant(VeinSmeltEnchantment(config.name.bake))) return

        val block = e.block
        val type = block.type

        // Автосмелт (якщо увімкнено)
        if (config.smeltEffect) {
            when (type) {
                Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE -> {
                    e.isDropItems = false
                    block.type = Material.AIR
                    block.world.dropItemNaturally(block.location, ItemStack(Material.IRON_INGOT))
                }
                Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE -> {
                    e.isDropItems = false
                    block.type = Material.AIR
                    block.world.dropItemNaturally(block.location, ItemStack(Material.GOLD_INGOT))
                }
                else -> {}
            }
        }

        // Вейнмайн (якщо увімкнено)
        if (config.veinEffect && type in ores) {
            val vein = VeinFinder.findConnectedBlocks(block, type, 27)
            for (b in vein) {
                if (b != block) b.breakNaturally(tool)
            }
        }
    }
}
