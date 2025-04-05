package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.config.Configs;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
abstract class EntityStepUpMixin {
    @Unique
    public boolean wallJumpStuffs$isEnableChangeMaxUpStep;

    @SuppressWarnings("unused")
    @Unique
    public void wallJumpStuffs$setEnableChangeMaxUpStep(boolean value) {
        wallJumpStuffs$isEnableChangeMaxUpStep = value;
    }

    @Redirect(method = "collide", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;maxUpStep()F"), order = Integer.MIN_VALUE)
    private float collideReplaceMaxUpStep(Entity instance) {
        if (!wallJumpStuffs$isEnableChangeMaxUpStep) return instance.maxUpStep();
        return Configs.stepUpConfig.stepUpHeight;
    }
}
