package io.github.yky.walljumpstuffs.mixin.client;

import com.mojang.authlib.GameProfile;
import io.github.yky.walljumpstuffs.config.Configs;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
abstract class ClientPlayerEntityUnlimitedSprintMixin extends AbstractClientPlayer {
    ClientPlayerEntityUnlimitedSprintMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Shadow
    protected abstract boolean hasEnoughImpulseToStartSprinting();

    @Shadow
    protected abstract boolean vehicleCanSprint(Entity vehicle);

    @Inject(method = "canStartSprinting", at = @At(value = "HEAD"), cancellable = true)
    private void unlimitedSprint(CallbackInfoReturnable<Boolean> cir) {
        if (!Configs.sprintConfig.unlimitedSprint) return;
        cir.setReturnValue(!this.isSprinting() && this.hasEnoughImpulseToStartSprinting() && (!this.isPassenger() || this.vehicleCanSprint(this.getVehicle())) && !this.isFallFlying());
        cir.cancel();
    }

    @Inject(method = "vehicleCanSprint", at = @At(value = "HEAD"), cancellable = true)
    private void unlimitedVehicleSprint(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!Configs.sprintConfig.unlimitedSprint) return;
        cir.setReturnValue(entity.isControlledByLocalInstance());
        cir.cancel();
    }

    @Inject(method = "hasEnoughFoodToStartSprinting", at = @At(value = "HEAD"), cancellable = true)
    private void unlimitedFood(CallbackInfoReturnable<Boolean> cir) {
        if (!Configs.sprintConfig.unlimitedSprint) return;
        cir.setReturnValue(true);
        cir.cancel();
    }
}
