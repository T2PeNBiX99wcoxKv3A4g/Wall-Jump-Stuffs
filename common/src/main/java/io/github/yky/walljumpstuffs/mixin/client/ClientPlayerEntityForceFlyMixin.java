package io.github.yky.walljumpstuffs.mixin.client;

import com.mojang.authlib.GameProfile;
import io.github.yky.walljumpstuffs.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Abilities;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
abstract class ClientPlayerEntityForceFlyMixin extends AbstractClientPlayer {
    @Shadow
    @Final
    protected Minecraft minecraft;

    ClientPlayerEntityForceFlyMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Redirect(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayfly:Z"))
    private boolean canFly(Abilities instance) {
        if (!Configs.flyConfig.unlimitedFly) return instance.mayfly;
        return true;
    }

    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;isAlwaysFlying()Z"))
    private boolean isAlwaysFlying(MultiPlayerGameMode instance) {
        if (!Configs.flyConfig.isAlwaysFlying) {
            if (this.minecraft.gameMode != null)
                return this.minecraft.gameMode.isAlwaysFlying();
            return false;
        }
        return true;
    }
}
