package io.github.yky.walljumpstuffs

import io.github.yky.walljumpstuffs.config.Configs
import net.minecraft.world.entity.Entity

object Utils {
    @JvmStatic
    fun replaceGetStepHeight(instance: Entity, isEnableChangeMaxUpStep: Boolean): Float {
        val oldStepHeight = instance.stepHeight
        val newStepHeight = Configs.stepUpConfig.stepUpHeight

        if (!isEnableChangeMaxUpStep) return oldStepHeight
        return newStepHeight.coerceAtLeast(oldStepHeight)
    }

    @JvmStatic
    fun replaceMaxUpStep(instance: Entity, isEnableChangeMaxUpStep: Boolean): Float {
        @Suppress("DEPRECATION") val oldStepHeight = instance.maxUpStep()
        val newStepHeight = Configs.stepUpConfig.stepUpHeight

        if (!isEnableChangeMaxUpStep) return oldStepHeight
        return newStepHeight.coerceAtLeast(oldStepHeight)
    }
}