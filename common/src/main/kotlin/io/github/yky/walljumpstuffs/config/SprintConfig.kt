package io.github.yky.walljumpstuffs.config

import io.github.yky.walljumpstuffs.Constants
import me.fzzyhmstrs.fzzy_config.annotations.NonSync
import me.fzzyhmstrs.fzzy_config.config.Config
import net.minecraft.resources.ResourceLocation

class SprintConfig : Config(ResourceLocation(Constants.MOD_ID, "sprint")) {
    @JvmField
    @NonSync
    var unlimitedSprint: Boolean = false
}