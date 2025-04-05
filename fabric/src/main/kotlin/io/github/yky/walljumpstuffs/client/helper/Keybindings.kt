package io.github.yky.walljumpstuffs.client.helper

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW

object Keybindings {
    @JvmField
    var CLING = KeyMapping(
        "key.walljumpstuff.cling",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_LEFT_SHIFT,
        "category.walljumpstuff.binds"
    )
}