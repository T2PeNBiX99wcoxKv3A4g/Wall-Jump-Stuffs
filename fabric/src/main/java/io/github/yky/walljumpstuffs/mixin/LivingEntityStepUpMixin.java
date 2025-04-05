package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.EntityStepUpInterface;
import io.github.yky.walljumpstuffs.Utils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
abstract class LivingEntityStepUpMixin implements EntityStepUpInterface {
    @Redirect(method = "maxUpStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;maxUpStep()F"), order = Integer.MIN_VALUE)
    private float replaceMaxUpStep(Entity instance) {
        return Utils.replaceMaxUpStep(instance, wallJumpStuffs$isEnableChangeMaxUpStep);
    }
}
