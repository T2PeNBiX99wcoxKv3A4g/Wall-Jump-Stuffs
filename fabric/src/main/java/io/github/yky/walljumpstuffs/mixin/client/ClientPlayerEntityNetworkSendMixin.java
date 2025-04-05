package io.github.yky.walljumpstuffs.mixin.client;

import io.github.yky.walljumpstuffs.Constants;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("unused")
@Mixin(LocalPlayer.class)
abstract class ClientPlayerEntityNetworkSendMixin {
    @Unique
    public void wallJumpStuffs$sendFallDistanceToServer(float fallDistance) {
        var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeFloat(fallDistance);
        ClientPlayNetworking.send(Constants.FALL_DISTANCE_PACKET_ID, passedData);
    }
}
