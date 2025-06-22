package me.nitkanikita.runeenchantments.config

import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.objectmapping.ObjectMapper
import org.spongepowered.configurate.serialize.TypeSerializerCollection
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.io.File

class ConfigLoader(private val configFile: File) {

    lateinit var config: PluginConfig
        private set

    private val loader = YamlConfigurationLoader.builder()
        .defaultOptions {
            it.serializers(
                TypeSerializerCollection.defaults().childBuilder()
                    .registerAnnotatedObjects(ObjectMapper.factory())
                    .build()
            )
        }
        .path(configFile.toPath())
        .build()

    fun load() {
        val node: ConfigurationNode = loader.load()
        config = node.get(PluginConfig::class.java) ?: PluginConfig()
    }

    fun save(config: PluginConfig) {
        val node = loader.load()
        node.set(PluginConfig::class.java, config)
        loader.save(node)
    }
}