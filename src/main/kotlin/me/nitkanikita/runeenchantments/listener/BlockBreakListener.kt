package me.nitkanikita.runeenchantments.listener

import me.nitkanikita.runeenchantments.config.ConfigLoader
import me.nitkanikita.runeenchantments.util.EnchantUtils
import me.nitkanikita.runeenchantments.util.VeinFinder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class BlockBreakListener(
    private val configLoader: ConfigLoader,
    private val plugin: Plugin,
) : Listener {
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
        if (!EnchantUtils.isEnchant(tool)) return

        val block = e.block
        val type = block.type

        if (e.player.gameMode == org.bukkit.GameMode.CREATIVE) return

        // Карта "Автосмелт" — з якого матеріалу що падати має
        val smeltMap = mapOf(
            Material.IRON_ORE to Material.IRON_INGOT,
            Material.DEEPSLATE_IRON_ORE to Material.IRON_INGOT,
            Material.GOLD_ORE to Material.GOLD_INGOT,
            Material.DEEPSLATE_GOLD_ORE to Material.GOLD_INGOT
        )

        // Якщо увімкнено автосмелт для одиничного блоку
        if (config.smeltEffect && type in smeltMap.keys) {
            e.isDropItems = false
            block.type = Material.AIR
            block.world.dropItemNaturally(block.location, ItemStack(smeltMap[type]!!))
        }

        // Вейнмайн (якщо увімкнено)
        if (config.veinEffect && type in ores) {
            val vein = VeinFinder.findConnectedBlocks(block, type, 27)
            Bukkit.getScheduler().runTask(plugin, Runnable {
                for (b in vein) {
                    if (b == block) continue // оригінальний блок вже оброблений вище

                    if (config.smeltEffect && b.type in smeltMap.keys) {
                        // Падає плавлений предмет
                        val dropType = smeltMap[b.type]!!
                        val loc = b.location
                        b.type = Material.AIR
                        b.world.dropItemNaturally(loc, ItemStack(dropType))
                    } else {
                        // Просто зламаємо звичайно без інструменту
                        b.breakNaturally(ItemStack.empty())
                    }
                }
            })
        }
    }
}
