package io.github.yky.walljumpstuffs.client

import io.github.yky.walljumpstuffs.client.helper.Keybindings
import io.github.yky.walljumpstuffs.config.Configs
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper

fun init() {
    if (Configs.wallJumpConfig.enableKeybind)
        KeyBindingHelper.registerKeyBinding(Keybindings.CLING)
}