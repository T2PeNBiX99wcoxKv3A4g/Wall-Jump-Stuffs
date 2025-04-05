package io.github.yky.walljumpstuffs.config

import io.github.yky.walljumpstuffs.Constants
import me.fzzyhmstrs.fzzy_config.config.Config
import net.minecraft.resources.ResourceLocation

class DamageConfig : Config(ResourceLocation(Constants.MOD_ID, "damage")) {
    @JvmField
    var isInvulnerable: Boolean = false
}