package io.github.yky.walljumpstuffs.mixin.client;

import com.mojang.authlib.GameProfile;
import io.github.yky.walljumpstuffs.client.ClientPlayerEntityNetworkHandleInterface;
import io.github.yky.walljumpstuffs.client.helper.Keybindings;
import io.github.yky.walljumpstuffs.config.Configs;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(LocalPlayer.class)
abstract class ClientPlayerEntityWallJumpMixin extends AbstractClientPlayer implements ClientPlayerEntityNetworkHandleInterface {
    ClientPlayerEntityWallJumpMixin(ClientLevel world, GameProfile profile, ProfilePublicKey ignoredPlayerPublicKey) {
        super(world, profile);
    }

    @Shadow
    public abstract boolean isHandsBusy();

    @Shadow
    public abstract float getViewYRot(float tickDelta);

    @Shadow
    public Input input;

    @Unique
    public int wallJumpStuffs$ticksWallClinged;
    @Unique
    private int wallJumpStuffs$ticksKeyDown;
    @Unique
    private double wallJumpStuffs$clingX;
    @Unique
    private double wallJumpStuffs$clingZ;
    @Unique
    private double wallJumpStuffs$lastJumpY = Double.MAX_VALUE;
    @Unique
    private Set<Direction> wallJumpStuffs$walls = new HashSet<>();
    @Unique
    private Set<Direction> wallJumpStuffs$staleWalls = new HashSet<>();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Unique
    private boolean wallJumpStuffs$isFree(AABB box) {
        return level().noCollision(this, box) && !level().containsAnyLiquid(box);
    }

    @Unique
    private boolean wallJumpStuffs$isKeyDown() {
        return Configs.wallJumpConfig.enableKeybind ? Keybindings.CLING.isDown() : this.input.shiftKeyDown;
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void wallJumpTickMovement(CallbackInfo ci) {
        wallJumpStuffs$doWallJump();
    }

    @Unique
    private void wallJumpStuffs$doWallJump() {
        if (!wallJumpStuffs$canWallJump())
            return;

        if (onGround() || getAbilities().flying || !level().getFluidState(blockPosition()).isEmpty() || isHandsBusy()) {
            wallJumpStuffs$ticksWallClinged = 0;
            wallJumpStuffs$clingX = Double.NaN;
            wallJumpStuffs$clingZ = Double.NaN;
            wallJumpStuffs$lastJumpY = Double.MAX_VALUE;
            wallJumpStuffs$staleWalls.clear();

            return;
        }

        wallJumpStuffs$updateWalls();
        wallJumpStuffs$ticksKeyDown = wallJumpStuffs$isKeyDown() ? wallJumpStuffs$ticksKeyDown + 1 : 0;

        if (wallJumpStuffs$ticksWallClinged < 1) {
            if (wallJumpStuffs$ticksKeyDown > 0 && wallJumpStuffs$ticksKeyDown < 4 && !wallJumpStuffs$walls.isEmpty() && wallJumpStuffs$canWallCling()) {

                walkAnimation.speed(2.5F);
                walkAnimation.speedOld = 2.5F;

                if (Configs.wallJumpConfig.jumpConfigs.autoRotation) {
                    setYRot(wallJumpStuffs$getClingDirection().getOpposite().toYRot());
                    yRotO = getYRot();
                }

                wallJumpStuffs$ticksWallClinged = 1;
                wallJumpStuffs$clingX = getX();
                wallJumpStuffs$clingZ = getZ();

                wallJumpStuffs$playHitSound(wallJumpStuffs$getWallPos());
                wallJumpStuffs$spawnWallParticle(wallJumpStuffs$getWallPos());
            }

            return;
        }

        if (!wallJumpStuffs$isKeyDown() || onGround() || !level().getFluidState(blockPosition()).isEmpty() || wallJumpStuffs$walls.isEmpty()) {
            wallJumpStuffs$ticksWallClinged = 0;

            if ((zza != 0 || xxa != 0) && !onGround() && !wallJumpStuffs$walls.isEmpty()) {
                fallDistance = 0.0F;
                wallJumpStuffs$wallJump(Configs.wallJumpConfig.jumpConfigs.wallJumpXextra, Configs.wallJumpConfig.jumpConfigs.wallJumpHeight, Configs.wallJumpConfig.jumpConfigs.wallJumpZextra);
                wallJumpStuffs$staleWalls = new HashSet<>(wallJumpStuffs$walls);
            }

            return;
        }

        if (Configs.wallJumpConfig.jumpConfigs.autoRotation) {
            setYRot(wallJumpStuffs$getClingDirection().getOpposite().toYRot());
            yRotO = getYRot();
        }

        setPosRaw(wallJumpStuffs$clingX, getY(), wallJumpStuffs$clingZ);

        var motionY = getDeltaMovement().y();

        if (motionY > 0.0)
            motionY = 0.0;
        else if (motionY < -0.6) {
            motionY = motionY + 0.2;
            wallJumpStuffs$spawnWallParticle(wallJumpStuffs$getWallPos());
        } else if (wallJumpStuffs$ticksWallClinged++ > Configs.wallJumpConfig.jumpConfigs.wallSlideDelay) {
            motionY = -0.1;
            wallJumpStuffs$spawnWallParticle(wallJumpStuffs$getWallPos());
        } else
            motionY = 0.0;

        if (fallDistance > 2) {
            fallDistance = 0;
            wallJumpStuffs$sendFallDistanceToServer((float) (motionY * motionY * 8));
        }

        setDeltaMovement(0.0, motionY, 0.0);
    }

    @Unique
    private boolean wallJumpStuffs$canWallJump() {
        return Configs.wallJumpConfig.jumpConfigs.useWallJump;
    }

    @Unique
    private boolean wallJumpStuffs$canWallCling() {
        if (onClimbable() || getDeltaMovement().y() > 0.1)
            return false;

        if (!wallJumpStuffs$isFree(getBoundingBox().move(0, -0.8, 0)))
            return false;

        if (Configs.wallJumpConfig.jumpConfigs.allowReClinging || (getY() < wallJumpStuffs$lastJumpY - 1 || getY() > wallJumpStuffs$lastJumpY + 1))
            return true;

        return !wallJumpStuffs$staleWalls.containsAll(wallJumpStuffs$walls);
    }

    @Unique
    private void wallJumpStuffs$updateWalls() {
        var box = new AABB(getX() - 0.001, getY(), getZ() - 0.001, getX() + 0.001, getY() + getEyeHeight(), getZ() + 0.001);
        var dist = (getBbWidth() / 2) + (wallJumpStuffs$ticksWallClinged > 0 ? 0.1 : 0.06);

        AABB[] axes = {box.expandTowards(0, 0, dist), box.expandTowards(-dist, 0, 0), box.expandTowards(0, 0, -dist), box.expandTowards(dist, 0, 0)};

        var i = 0;
        Direction direction;
        wallJumpStuffs$walls = new HashSet<>();

        for (var axis : axes) {
            direction = Direction.from2DDataValue(i++);
            if (!wallJumpStuffs$isFree(axis)) {
                wallJumpStuffs$walls.add(direction);
                horizontalCollision = true;
            }
        }
    }

    @Unique
    private Direction wallJumpStuffs$getClingDirection() {
        return wallJumpStuffs$walls.isEmpty() ? Direction.UP : wallJumpStuffs$walls.iterator().next();
    }

    @Unique
    private BlockPos wallJumpStuffs$getWallPos() {
        var clingPos = blockPosition().relative(wallJumpStuffs$getClingDirection());
        return level().getBlockState(clingPos).calculateSolid() ? clingPos : clingPos.relative(Direction.UP);
    }

    @Unique
    private void wallJumpStuffs$wallJump(float x, float up, float z) {
        var strafe = Math.signum(xxa) * up * up;
        var forward = Math.signum(zza) * up * up;
        var f = 1.0F / Mth.sqrt(strafe * strafe + up * up + forward * forward);
        strafe = strafe * f;
        forward = forward * f;

        var f1 = Mth.sin(getYHeadRot() * 0.017453292F) * 0.45F;
        var f2 = Mth.cos(getYHeadRot() * 0.017453292F) * 0.45F;

        var jumpBoostLevel = 0;
        var jumpBoostEffect = getEffect(MobEffects.JUMP);
        if (jumpBoostEffect != null)
            jumpBoostLevel = jumpBoostEffect.getAmplifier() + 1;

        var motion = getDeltaMovement();
        setDeltaMovement(motion.x() + (strafe * f2 - forward * f1) + x + (jumpBoostLevel * 0.125), up + (jumpBoostLevel * 0.125), motion.z() + (forward * f2 + strafe * f1) + z + (jumpBoostLevel * 0.125));

        wallJumpStuffs$lastJumpY = getY();
        wallJumpStuffs$playBreakSound(wallJumpStuffs$getWallPos());
        wallJumpStuffs$spawnWallParticle(wallJumpStuffs$getWallPos());
    }

    @Unique
    private void wallJumpStuffs$playHitSound(BlockPos blockPos) {
        var blockState = level().getBlockState(blockPos);
        var soundType = blockState.getBlock().getSoundType(blockState);
        playSound(soundType.getHitSound(), soundType.getVolume() * 0.25F, soundType.getPitch());
    }

    @Unique
    private void wallJumpStuffs$playBreakSound(BlockPos blockPos) {
        var blockState = level().getBlockState(blockPos);
        var soundType = blockState.getBlock().getSoundType(blockState);
        playSound(soundType.getFallSound(), soundType.getVolume() * 0.5F, soundType.getPitch());
    }

    @Unique
    private void wallJumpStuffs$spawnWallParticle(BlockPos blockPos) {
        var blockState = level().getBlockState(blockPos);
        if (blockState.getRenderShape() != RenderShape.INVISIBLE) {
            var pos = position();
            var motion = wallJumpStuffs$getClingDirection().getNormal();
            level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), pos.x(), pos.y(), pos.z(), motion.getX() * -1.0D, -1.0D, motion.getZ() * -1.0D);
        }
    }
}
