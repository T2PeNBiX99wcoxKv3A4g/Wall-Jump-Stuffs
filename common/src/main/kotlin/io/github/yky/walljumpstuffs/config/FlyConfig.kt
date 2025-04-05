package io.github.yky.walljumpstuffs.config

import io.github.yky.walljumpstuffs.Constants
import me.fzzyhmstrs.fzzy_config.annotations.NonSync
import me.fzzyhmstrs.fzzy_config.config.Config
import net.minecraft.resources.ResourceLocation

class FlyConfig : Config(ResourceLocation(Constants.MOD_ID, "fly")) {
    @JvmField
    @NonSync
    var unlimitedFly: Boolean = false

    @JvmField
    @NonSync
    var isAlwaysFlying: Boolean = false
}