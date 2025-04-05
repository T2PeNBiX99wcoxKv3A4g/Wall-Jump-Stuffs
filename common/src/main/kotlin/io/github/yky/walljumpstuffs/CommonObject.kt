package io.github.yky.walljumpstuffs

import io.github.yky.walljumpstuffs.config.Configs

object CommonObject {
    fun init() {
        Constants.Logger.info("Load Config $Configs")
    }
}