package io.github.yky.walljumpstuffs.mixin.client;

import io.github.yky.walljumpstuffs.WallJumpStuffs;
import io.github.yky.walljumpstuffs.network.PlayerFallDistanceHandle;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("unused")
@Mixin(LocalPlayer.class)
abstract class ClientPlayerEntityNetworkSendMixin {
    @Unique
    public void wallJumpStuffs$sendFallDistanceToServer(float fallDistance) {
        WallJumpStuffs.channel.sendToServer(new PlayerFallDistanceHandle(fallDistance));
    }
}
