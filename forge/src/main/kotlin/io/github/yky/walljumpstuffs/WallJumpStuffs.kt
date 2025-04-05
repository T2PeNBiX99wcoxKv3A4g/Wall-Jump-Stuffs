package io.github.yky.walljumpstuffs

import io.github.yky.walljumpstuffs.client.helper.Keybindings
import io.github.yky.walljumpstuffs.client.init
import io.github.yky.walljumpstuffs.config.Configs
import io.github.yky.walljumpstuffs.network.PlayerFallDistanceHandle
import net.minecraftforge.client.event.RegisterKeyMappingsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.simple.SimpleChannel
import thedarkcolour.kotlinforforge.forge.MOD_BUS

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
        MOD_BUS.register(this)

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

    @SubscribeEvent
    fun registerBindings(event: RegisterKeyMappingsEvent) {
        if (Configs.wallJumpConfig.enableKeybind)
            event.register(Keybindings.CLING)
    }

    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent) {
        init()
    }
}