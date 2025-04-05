package io.github.yky.walljumpstuffs.mixin.client;

import com.mojang.authlib.GameProfile;
import io.github.yky.walljumpstuffs.client.ClientPlayerEntityNetworkHandleInterface;
import io.github.yky.walljumpstuffs.client.ClientPlayerEntityWallJumpInterface;
import io.github.yky.walljumpstuffs.config.Configs;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
abstract class ClientPlayerEntityDoubleJumpMixin extends AbstractClientPlayer implements ClientPlayerEntityWallJumpInterface, ClientPlayerEntityNetworkHandleInterface {
    @Shadow
    public abstract boolean isHandsBusy();

    @Shadow
    public Input input;

    @Unique
    private int wallJumpStuffs$jumpCount = 0;
    @Unique
    private boolean wallJumpStuffs$jumpKey = false;

    ClientPlayerEntityDoubleJumpMixin(ClientLevel world, GameProfile profile, ProfilePublicKey ignoredPlayerPublicKey) {
        super(world, profile);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void doubleJumpTickMovement(CallbackInfo ci) {
        this.wallJumpStuffs$doDoubleJump();
    }

    @SuppressWarnings("AccessStaticViaInstance")
    @Unique
    private void wallJumpStuffs$doDoubleJump() {
        var pos = this.position();
        var motion = this.getDeltaMovement();

        var box = new AABB(pos.x(), pos.y() + this.getEyeHeight(this.getPose()) * 0.8, pos.z(), pos.x(), pos.y() + this.getBbHeight(), pos.z());

        if (this.onGround() || this.level().containsAnyLiquid(box) || this.wallJumpStuffs$ticksWallClinged > 0 || this.isHandsBusy() || this.getAbilities().mayfly) {
            this.wallJumpStuffs$jumpCount = this.wallJumpStuffs$getMultiJumps();
        } else if (this.input.jumping) {
            if (!this.wallJumpStuffs$jumpKey && (this.wallJumpStuffs$jumpCount > 0 || Configs.wallJumpConfig.doubleConfigs.unlimitedJump) && motion.y() < 0.333 && this.wallJumpStuffs$ticksWallClinged < 1) {
                this.jumpFromGround();

                if (!Configs.wallJumpConfig.doubleConfigs.unlimitedJump)
                    this.wallJumpStuffs$jumpCount--;

                this.fallDistance = 0.0F;

                wallJumpStuffs$sendFallDistanceToServer(this.fallDistance);
            }
            this.wallJumpStuffs$jumpKey = true;
        } else {
            this.wallJumpStuffs$jumpKey = false;
        }
    }

    @Unique
    private int wallJumpStuffs$getMultiJumps() {
        var jumpCount = 0;

        if (Configs.wallJumpConfig.doubleConfigs.useDoubleJump)
            jumpCount += 1;

        jumpCount += Configs.wallJumpConfig.doubleConfigs.addJumpCount;

        return jumpCount;
    }
}
