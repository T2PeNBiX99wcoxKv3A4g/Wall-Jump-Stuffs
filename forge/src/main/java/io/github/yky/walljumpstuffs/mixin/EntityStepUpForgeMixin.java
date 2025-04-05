package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.Utils;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
abstract class EntityStepUpForgeMixin {
    @Unique
    public boolean wallJumpStuffs$isEnableChangeMaxUpStep;

    @SuppressWarnings("unused")
    @Unique
    public void wallJumpStuffs$setEnableChangeMaxUpStep(boolean value) {
        wallJumpStuffs$isEnableChangeMaxUpStep = value;
    }

    // https://github.com/Shadows-of-Fire/Apotheosis/blob/c502c2a6c15d6a6ff9728acadccc85172f58cfe5/src/main/java/shadows/apotheosis/mixin/IForgeEntityMixin.java#L25
    // Very cool, I think the only thing I can do is redirect all of "getStepHeight" method to bypass the shit
    @Redirect(method = "collide", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getStepHeight()F", remap = false))
    private float replaceMaxUpStep(Entity instance) {
        return Utils.replaceGetStepHeight(instance, wallJumpStuffs$isEnableChangeMaxUpStep);
    }
}
