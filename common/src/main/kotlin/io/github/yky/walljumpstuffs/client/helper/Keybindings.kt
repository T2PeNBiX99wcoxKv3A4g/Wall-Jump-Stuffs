package io.github.yky.walljumpstuffs.client.helper

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW

object Keybindings {
    @JvmField
    val CLING = KeyMapping(
        "key.walljumpstuffs.cling",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_LEFT_SHIFT,
        "category.walljumpstuffs.binds"
    )
}