package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.config.Configs;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
class PlayerEntityDamageMixin {
    @Inject(method = "isInvulnerableTo", at = @At(value = "HEAD"), cancellable = true)
    private void isInvulnerable(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (!Configs.damageConfig.isInvulnerable) return;
        cir.setReturnValue(true);
        cir.cancel();
    }
}
