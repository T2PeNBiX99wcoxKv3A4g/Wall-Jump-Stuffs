package io.github.yky.walljumpstuffs.mixin.client;

import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("unused")
@Mixin(LocalPlayer.class)
abstract class ClientPlayerEntityIsKeyDownMixin {
    @Shadow
    public Input input;

    @Unique
    private boolean wallJumpStuffs$isKeyDown() {
//        return Configs.wallJumpConfig.enableKeybind ? Keybindings.CLING.isDown() : this.input.shiftKeyDown;
        return this.input.shiftKeyDown;
    }
}
