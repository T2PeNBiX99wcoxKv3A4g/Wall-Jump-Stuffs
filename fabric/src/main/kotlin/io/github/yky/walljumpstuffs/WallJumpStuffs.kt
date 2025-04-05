package io.github.yky.walljumpstuffs

import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl

fun init() {
    CommonObject.init()

    // Packets
    ServerPlayNetworking.registerGlobalReceiver(Constants.FALL_DISTANCE_PACKET_ID) { server: MinecraftServer, player: ServerPlayer, _: ServerGamePacketListenerImpl, buf: FriendlyByteBuf, _: PacketSender ->
        val fallDistance = buf.readFloat()
        server.execute {
            player.fallDistance = fallDistance
        }
    }
}