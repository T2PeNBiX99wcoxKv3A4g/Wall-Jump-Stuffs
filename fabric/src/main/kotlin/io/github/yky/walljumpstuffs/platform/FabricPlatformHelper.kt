package io.github.yky.walljumpstuffs.platform

import io.github.yky.walljumpstuffs.platform.services.IPlatformHelper
import net.fabricmc.loader.api.FabricLoader

class FabricPlatformHelper : IPlatformHelper {
    override fun getPlatformName() = "Fabric"
    override fun isModLoaded(modId: String) = FabricLoader.getInstance().isModLoaded(modId)
    override fun isDevelopmentEnvironment() = FabricLoader.getInstance().isDevelopmentEnvironment
}