package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.Utils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
abstract class LivingEntityStepUpMixin {
    @Unique
    public boolean wallJumpStuffs$isEnableChangeMaxUpStep;

    @SuppressWarnings("unused")
    @Unique
    public void wallJumpStuffs$setEnableChangeMaxUpStep(boolean value) {
        wallJumpStuffs$isEnableChangeMaxUpStep = value;
    }

    @Redirect(method = "maxUpStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;maxUpStep()F"), order = Integer.MIN_VALUE)
    private float replaceMaxUpStep(Entity instance) {
        return Utils.replaceMaxUpStep(instance, wallJumpStuffs$isEnableChangeMaxUpStep);
    }
}
