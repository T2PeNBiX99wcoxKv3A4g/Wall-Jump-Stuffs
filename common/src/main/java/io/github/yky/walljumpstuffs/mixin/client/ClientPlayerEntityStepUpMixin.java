package io.github.yky.walljumpstuffs.mixin.client;

import com.mojang.authlib.GameProfile;
import io.github.yky.walljumpstuffs.EntityStepUpInterface;
import io.github.yky.walljumpstuffs.config.Configs;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
abstract class ClientPlayerEntityStepUpMixin extends AbstractClientPlayer implements EntityStepUpInterface {
    ClientPlayerEntityStepUpMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Shadow
    public abstract boolean isShiftKeyDown();

    @Unique
    private boolean wallJumpStuffs$isStepUpEnable() {
        if (!Configs.stepUpConfig.enableStepUp) return false;
        return !isShiftKeyDown() || Configs.stepUpConfig.enableStepUpWhenSneaking;
    }

    // Step up, should apply before auto jump thereby "disabling" auto jump
    @Inject(method = "move", at = @At(value = "HEAD"))
    private void enableStepUpInjectFirst(MoverType movementType, Vec3 movement, CallbackInfo ci) {
        if (!Configs.stepUpConfig.enableStepUp) return;
        this.wallJumpStuffs$setEnableChangeMaxUpStep(wallJumpStuffs$isStepUpEnable());
    }

    @Inject(method = "canAutoJump", at = @At(value = "HEAD"), cancellable = true)
    private void disableAutoJump(CallbackInfoReturnable<Boolean> cir) {
        if (Configs.stepUpConfig.enableStepUp && wallJumpStuffs$isStepUpEnable()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
