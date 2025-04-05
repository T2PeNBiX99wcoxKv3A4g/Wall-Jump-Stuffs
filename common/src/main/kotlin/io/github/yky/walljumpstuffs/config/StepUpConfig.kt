package io.github.yky.walljumpstuffs.config

import io.github.yky.walljumpstuffs.Constants
import me.fzzyhmstrs.fzzy_config.annotations.NonSync
import me.fzzyhmstrs.fzzy_config.config.Config
import net.minecraft.resources.ResourceLocation

class StepUpConfig : Config(ResourceLocation(Constants.MOD_ID, "step_up")) {
    @JvmField
    @NonSync
    var enableStepUp: Boolean = true

    @JvmField
    @NonSync
    var enableStepUpWhenSneaking: Boolean = false

    @JvmField
    var stepUpHeight = 1.25f
}