package io.github.yky.walljumpstuffs.mixin.client;

import com.mojang.authlib.GameProfile;
import io.github.yky.walljumpstuffs.config.Configs;
import io.github.yky.walljumpstuffs.client.FallingSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
abstract class ClientPlayerEntityMiscellaneousMixin extends AbstractClientPlayer {
    ClientPlayerEntityMiscellaneousMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Unique
    private boolean wallJumpStuffs$isFree(AABB box) {
        return level().noCollision(this, box) && !this.level().containsAnyLiquid(box);
    }

    @Unique
    private FallingSound wallJumpStuffs$fallingSound;

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void miscellaneousTickMovement(CallbackInfo ci) {
        var self = (LocalPlayer) (Object) this;

        if (wallJumpStuffs$fallingSound == null)
            wallJumpStuffs$fallingSound = new FallingSound(self, RandomSource.create());

        if (this.horizontalCollision && Configs.wallJumpConfig.stepAssist && this.getDeltaMovement().y() > -0.2 && this.getDeltaMovement().y() < 0.01)
            if (this.wallJumpStuffs$isFree(this.getBoundingBox().inflate(0.01, -this.maxUpStep() + 0.02, 0.01)))
                this.setOnGround(true);

        if (this.fallDistance > 1.5 && !this.isFallFlying()) {
            if (Configs.wallJumpConfig.playFallSound && wallJumpStuffs$fallingSound.isStopped()) {
                wallJumpStuffs$fallingSound = new FallingSound(self, RandomSource.create());
                Minecraft.getInstance().getSoundManager().play(wallJumpStuffs$fallingSound);
            }
        } else if (!wallJumpStuffs$fallingSound.isStopped()) {
            wallJumpStuffs$fallingSound.stop2();
        }
    }
}
