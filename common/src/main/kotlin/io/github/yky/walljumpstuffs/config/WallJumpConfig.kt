package io.github.yky.walljumpstuffs.config

import io.github.yky.walljumpstuffs.Constants
import me.fzzyhmstrs.fzzy_config.annotations.Action
import me.fzzyhmstrs.fzzy_config.annotations.NonSync
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigSection
import net.minecraft.resources.ResourceLocation

class WallJumpConfig : Config(ResourceLocation(Constants.MOD_ID, "wall_jump")) {
    @JvmField
    var doubleConfigs = DoubleConfigs()

    class DoubleConfigs : ConfigSection() {
        @JvmField
        @NonSync
        var useDoubleJump: Boolean = true

        @JvmField
        var minFallDistance: Float = 7.5f

        @JvmField
        var noFallDamage: Boolean = false

        @JvmField
        @NonSync
        var addJumpCount: Int = 0

        @JvmField
        @NonSync
        var unlimitedJump: Boolean = false
    }

    @JvmField
    @NonSync
    var speedConfigs = SpeedConfigs()

    class SpeedConfigs : ConfigSection() {
        @JvmField
        @NonSync
        var sprintSpeedBoost: Float = 0.375f

        @JvmField
        @NonSync
        var elytraSpeedBoost: Float = 0.75f
    }

    @JvmField
    @NonSync
    var jumpConfigs = JumpConfigs()

    class JumpConfigs : ConfigSection() {
        @JvmField
        @NonSync
        var useWallJump: Boolean = true

        @JvmField
        @NonSync
        var wallJumpHeight: Float = 0.55f

        @JvmField
        @NonSync
        var wallJumpXextra: Float = 0.0f

        @JvmField
        @NonSync
        var wallJumpZextra: Float = 0.0f

        @JvmField
        @NonSync
        var allowReClinging: Boolean = false

        @JvmField
        @NonSync
        var wallSlideDelay: Int = 15

        @JvmField
        @NonSync
        var autoRotation: Boolean = false
    }

    @JvmField
    @NonSync
    var playFallSound: Boolean = true

    @JvmField
    @NonSync
    var stepAssist: Boolean = true

    @JvmField
    @NonSync
    @RequiresAction(action = Action.RESTART)
    var enableKeybind: Boolean = false
}