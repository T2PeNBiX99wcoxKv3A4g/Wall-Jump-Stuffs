package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.EntityStepUpInterface;
import io.github.yky.walljumpstuffs.Utils;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AmphibiousNodeEvaluator.class)
abstract class AmphibiousNodeEvaluatorStepUpMixin implements EntityStepUpInterface {
    @Redirect(method = "getNeighbors", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;maxUpStep()F"), order = Integer.MIN_VALUE)
    private float replaceMaxUpStep(Mob instance) {
        return Utils.replaceMaxUpStep(instance, wallJumpStuffs$isEnableChangeMaxUpStep);
    }
}
