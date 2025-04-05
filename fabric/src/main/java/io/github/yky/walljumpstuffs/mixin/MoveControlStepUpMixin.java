package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.EntityStepUpInterface;
import io.github.yky.walljumpstuffs.Utils;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MoveControl.class)
abstract class MoveControlStepUpMixin implements EntityStepUpInterface {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;maxUpStep()F"), order = Integer.MIN_VALUE)
    private float replaceMaxUpStep(Mob instance) {
        return Utils.replaceMaxUpStep(instance, wallJumpStuffs$isEnableChangeMaxUpStep);
    }
}
