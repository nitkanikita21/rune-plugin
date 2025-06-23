package me.nitkanikita.runeenchantments

import me.nitkanikita.runeenchantments.command.Commands
import me.nitkanikita.runeenchantments.config.ConfigLoader
import me.nitkanikita.runeenchantments.config.PluginConfig
import me.nitkanikita.runeenchantments.listener.AnvilEnchantListener
import me.nitkanikita.runeenchantments.listener.BlockBreakListener
import me.nitkanikita.runeenchantments.listener.LootListener
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.CommandManager
import org.incendo.cloud.SenderMapper
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.minecraft.extras.MinecraftHelp
import org.incendo.cloud.paper.LegacyPaperCommandManager
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper
import org.incendo.cloud.paper.util.sender.Source
import java.io.File

class RuneEnchantmentsPlugin: JavaPlugin() {

    override fun onEnable() {


        try {
            val configLoader = initConfig()
            val commandManager = initCommandManager()

            Commands(configLoader, commandManager)

            server.pluginManager.registerEvents(BlockBreakListener(configLoader, this), this)
            server.pluginManager.registerEvents(LootListener(configLoader), this)
            server.pluginManager.registerEvents(AnvilEnchantListener(configLoader), this)
            logger.info("VeinSmelt plugin enabled.")
        } catch (e: Exception) {
            logger.severe("Failed to enable VeinSmelt plugin: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun onDisable() {
        logger.info("VeinSmelt plugin disabled.")
    }

    private fun initConfig(): ConfigLoader {
        val cfgFile = File(dataFolder, "config.yml")
        val configLoader = ConfigLoader(cfgFile)

        if(!cfgFile.exists()) {
            configLoader.save(PluginConfig())
        }
        return configLoader.apply { load() }
    }

    private fun initCommandManager(): CommandManager<CommandSender> {
        return LegacyPaperCommandManager(this, ExecutionCoordinator.simpleCoordinator(), SenderMapper.identity())
    }
}