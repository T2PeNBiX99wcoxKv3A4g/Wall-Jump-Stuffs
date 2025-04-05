package io.github.yky.walljumpstuffs

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Constants {
    @Suppress("SpellCheckingInspection")
    const val MOD_ID = "walljumpstuffs"

    @Suppress("MemberVisibilityCanBePrivate")
    const val MOD_NAME = "WallJumpStuffs"
    const val PROTOCOL_VERSION = "1"

    @JvmField
    val Logger: Logger = LoggerFactory.getLogger(MOD_NAME)

    @Suppress("SpellCheckingInspection")
    @JvmField
    val FALL_DISTANCE_PACKET_ID: ResourceLocation = ResourceLocation(MOD_ID, "falldistance")

    private var networkID: Int = -1

    val NetworkID get() = networkID
}