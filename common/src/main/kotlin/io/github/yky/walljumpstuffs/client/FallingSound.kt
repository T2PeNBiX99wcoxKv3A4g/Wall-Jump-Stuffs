package io.github.yky.walljumpstuffs.client

import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource

class FallingSound(private var player: LocalPlayer?, random: RandomSource) :
    AbstractTickableSoundInstance(SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, random) {

    init {
        this.looping = true
        this.delay = 0
        this.volume = Float.MIN_VALUE
    }

    override fun tick() {
        if (player == null) {
            this.stop()
            return
        }

        val length = player!!.deltaMovement.lengthSqr().toFloat()
        if (length >= 1.0 && player!!.isAlive) {
            this.volume = Mth.clamp((length - 1.0f) / 4.0f, 0.0f, 2.0f)
            if (this.volume > 0.8) this.pitch = 1.0f + (this.volume - 0.8f)
            else this.pitch = 1.0f
        } else {
            this.stop()
        }
    }

    fun stop2() {
        stop()
    }
}