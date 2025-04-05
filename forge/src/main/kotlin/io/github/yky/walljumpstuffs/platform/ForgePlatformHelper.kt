package io.github.yky.walljumpstuffs.platform

import io.github.yky.walljumpstuffs.platform.services.IPlatformHelper
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.FMLLoader

class ForgePlatformHelper : IPlatformHelper {
    override fun getPlatformName() = "Forge"
    override fun isModLoaded(modId: String) = ModList.get().isLoaded(modId)
    override fun isDevelopmentEnvironment() = !FMLLoader.isProduction()
}