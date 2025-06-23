package me.nitkanikita.runeenchantments.config

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
class PluginConfig {
    @Comment("Enchantment display name")
    var name: RawComponent = RawComponent("<light_purple>Rune: VeinSmelt")

    @Comment("Enchantment lore")
    var lore: RawComponent = RawComponent(
        "line 1",
        "line 2",
        "line 3"
    )
    var enchantmentLine = RawComponent("<i:false><gray>VeinSmelt I")
    var smeltEffect: Boolean = true
    var veinEffect: Boolean = true
    var chance = 1f
}