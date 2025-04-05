package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.Utils;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WalkNodeEvaluator.class)
abstract class WalkNodeEvaluatorStepUpForgeMixin {
    @Unique
    public boolean wallJumpStuffs$isEnableChangeMaxUpStep;

    @SuppressWarnings("unused")
    @Unique
    public void wallJumpStuffs$setEnableChangeMaxUpStep(boolean value) {
        wallJumpStuffs$isEnableChangeMaxUpStep = value;
    }

    @Redirect(method = {"getNeighbors", "getMobJumpHeight"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getStepHeight()F", remap = false))
    private float replaceMaxUpStep(Mob instance) {
        return Utils.replaceGetStepHeight(instance, wallJumpStuffs$isEnableChangeMaxUpStep);
    }
}
