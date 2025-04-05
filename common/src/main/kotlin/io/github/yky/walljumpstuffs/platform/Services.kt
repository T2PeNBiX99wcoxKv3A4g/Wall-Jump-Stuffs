package io.github.yky.walljumpstuffs.platform

import io.github.yky.walljumpstuffs.Constants
import io.github.yky.walljumpstuffs.platform.services.IPlatformHelper
import java.util.*

object Services {
    // In this example we provide a platform helper which provides information about what platform the mod is running on.
    // For example this can be used to check if the code is running on Forge vs Fabric, or to ask the modloader if another
    // mod is loaded.
    val PLATFORM: IPlatformHelper = load(
        IPlatformHelper::class.java
    )

    // This code is used to load a service for the current environment. Your implementation of the service must be defined
    // manually by including a text file in META-INF/services named with the fully qualified class name of the service.
    // Inside the file you should write the fully qualified class name of the implementation to load for the platform. For
    // example our file on Forge points to ForgePlatformHelper while Fabric points to FabricPlatformHelper.
    fun <T> load(clazz: Class<T>): T {
        val loadedService = ServiceLoader.load(clazz)
            .findFirst()
            .orElseThrow { NullPointerException("Failed to load service for " + clazz.name) }
        Constants.Logger.debug("Loaded {} for service {}", loadedService, clazz)
        return loadedService
    }
}