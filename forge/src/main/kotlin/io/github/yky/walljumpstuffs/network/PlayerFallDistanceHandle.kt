package io.github.yky.walljumpstuffs.network

import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class PlayerFallDistanceHandle(private val fallDistance: Float) {
    companion object {
        fun decode(buf: FriendlyByteBuf): PlayerFallDistanceHandle {
            return PlayerFallDistanceHandle(buf.readFloat())
        }
    }

    fun encode(buf: FriendlyByteBuf) {
        buf.writeFloat(fallDistance)
    }

    fun handle(ctx: Supplier<NetworkEvent.Context>) {
        val player = ctx.get().sender
        ctx.get().enqueueWork {
            player?.fallDistance = fallDistance
        }
        ctx.get().packetHandled = true
    }
}