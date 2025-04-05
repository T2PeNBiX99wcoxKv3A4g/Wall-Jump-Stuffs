package io.github.yky.walljumpstuffs.mixin.client;

import com.mojang.authlib.GameProfile;
import io.github.yky.walljumpstuffs.config.Configs;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
abstract class ClientPlayerEntitySpeedBoostMixin extends AbstractClientPlayer {
    @Shadow
    public abstract boolean isShiftKeyDown();

    ClientPlayerEntitySpeedBoostMixin(ClientLevel world, GameProfile profile, ProfilePublicKey ignoredPlayerPublicKey) {
        super(world, profile);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void miscellaneousTickMovement(CallbackInfo ci) {
        this.wallJumpStuffs$doSpeedBoost();
    }

    @Unique
    private void wallJumpStuffs$doSpeedBoost() {
        var self = (LocalPlayer) (Object) this;
        var jumpBoostEffect = self.getEffect(MobEffects.JUMP);
        var jumpBoostLevel = 0;
        if (jumpBoostEffect != null)
            jumpBoostLevel = jumpBoostEffect.getAmplifier() + 1;

        this.getAbilities().setFlyingSpeed((float) (this.getSpeed() * (this.isSprinting() ? 1 : 1.3) / 5) * (jumpBoostLevel * 0.5F + 1));

        var pos = this.position();
        var look = this.getLookAngle();
        var motion = this.getDeltaMovement();

        if (this.isFallFlying()) {
            if (this.isShiftKeyDown())
                if (this.getXRot() < 30F)
                    this.setDeltaMovement(motion.subtract(motion.scale(0.05)));
                else if (this.isSprinting()) {
                    var elytraSpeedBoost = Configs.wallJumpConfig.speedConfigs.elytraSpeedBoost;
                    var boost = new Vec3(look.x(), look.y() + 0.5, look.z()).normalize().scale(elytraSpeedBoost);
                    if (motion.length() <= boost.length())
                        this.setDeltaMovement(motion.add(boost.scale(0.05)));
                    if (boost.length() > 0.5)
                        this.level().addParticle(ParticleTypes.FIREWORK, pos.x(), pos.y(), pos.z(), 0, 0, 0);
                }

        } else if (this.isSprinting()) {
            var sprintSpeedBoost = Configs.wallJumpConfig.speedConfigs.sprintSpeedBoost;
            if (!this.onGround())
                sprintSpeedBoost /= 3.125f;
            var boost = new Vec3(look.x(), 0.0, look.z()).scale(sprintSpeedBoost * 0.125F);
            this.setDeltaMovement(motion.add(boost));
        }
    }
}
