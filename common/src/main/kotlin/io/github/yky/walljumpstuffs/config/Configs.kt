package io.github.yky.walljumpstuffs.config

import me.fzzyhmstrs.fzzy_config.api.ConfigApi

object Configs {
    @JvmField
    val stepUpConfig = ConfigApi.registerAndLoadConfig(::StepUpConfig)

    @JvmField
    val wallJumpConfig = ConfigApi.registerAndLoadConfig(::WallJumpConfig)

    @JvmField
    val sprintConfig = ConfigApi.registerAndLoadConfig(::SprintConfig)

    @JvmField
    val flyConfig = ConfigApi.registerAndLoadConfig(::FlyConfig)

    @JvmField
    val damageConfig = ConfigApi.registerAndLoadConfig(::DamageConfig)
}