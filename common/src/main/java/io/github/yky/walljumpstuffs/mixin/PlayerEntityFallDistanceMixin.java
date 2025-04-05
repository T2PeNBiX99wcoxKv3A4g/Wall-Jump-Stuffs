package io.github.yky.walljumpstuffs.mixin;

import io.github.yky.walljumpstuffs.config.Configs;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
abstract class PlayerEntityFallDistanceMixin {
    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @ModifyArg(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z"), index = 0)
    private float adjustFallDistance(float value) {
        if (value > 3 && (value <= Configs.wallJumpConfig.doubleConfigs.minFallDistance || Configs.wallJumpConfig.doubleConfigs.noFallDamage)) {
            this.playSound(SoundEvents.GENERIC_SMALL_FALL, 0.5f, 1.0f);
            return 3.0f;
        }

        if (Configs.wallJumpConfig.doubleConfigs.noFallDamage)
            return 3.0f;
        return value;
    }
}
