package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.Utils;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AmphibiousNodeEvaluator.class)
abstract class AmphibiousNodeEvaluatorStepUpMixin {
    @Unique
    public boolean wallJumpStuffs$isEnableChangeMaxUpStep;

    @SuppressWarnings("unused")
    @Unique
    public void wallJumpStuffs$setEnableChangeMaxUpStep(boolean value) {
        wallJumpStuffs$isEnableChangeMaxUpStep = value;
    }

    @Redirect(method = "getNeighbors", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;maxUpStep()F"), order = Integer.MIN_VALUE)
    private float replaceMaxUpStep(Mob instance) {
        return Utils.replaceMaxUpStep(instance, wallJumpStuffs$isEnableChangeMaxUpStep);
    }
}
