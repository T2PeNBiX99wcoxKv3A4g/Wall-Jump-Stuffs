package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.EntityStepUpInterface;
import io.github.yky.walljumpstuffs.Utils;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
abstract class PlayerStepUpForgeMixin implements EntityStepUpInterface {
    @Redirect(method = {"maybeBackOffFromEdge", "isAboveGround"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;maxUpStep()F"))
    private float replaceMaxUpStep(Player instance) {
        return Utils.replaceMaxUpStep(instance, wallJumpStuffs$isEnableChangeMaxUpStep);
    }
}
