package io.github.yky.walljumpstuffs

import io.github.yky.walljumpstuffs.network.PlayerFallDistanceHandle
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.simple.SimpleChannel

@Mod(Constants.MOD_ID)
class WallJumpStuffs {
    companion object {
        @JvmField
        val channel: SimpleChannel = NetworkRegistry.newSimpleChannel(
            Constants.FALL_DISTANCE_PACKET_ID,
            { Constants.PROTOCOL_VERSION },
            Constants.PROTOCOL_VERSION::equals,
            Constants.PROTOCOL_VERSION::equals
        )
    }

    init {
        CommonObject.init()

        // WTF Forge
        // Packets
        channel.registerMessage(
            Constants.NetworkID,
            PlayerFallDistanceHandle::class.java,
            PlayerFallDistanceHandle::encode,
            PlayerFallDistanceHandle::decode,
            PlayerFallDistanceHandle::handle
        )
    }
}