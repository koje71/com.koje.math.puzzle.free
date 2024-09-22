package com.koje.puzzle.core

import android.media.AudioAttributes
import android.media.SoundPool
import com.koje.framework.App
import com.koje.framework.utils.BooleanPreference
import com.koje.framework.utils.Logger
import com.koje.math.puzzle.R


object Sound {

    class Player(val id: Int, val trackVolume: Float) {
        val content = soundPool.load(App.instance, id, 1)

        fun play() {
            if (enabled.get()) {
                val name = App.instance.resources.getResourceName(id)
                Logger.info(this, "playing: $name")

                val volume = systemVolume * trackVolume
                soundPool.play(content, volume, volume, 1, 0, 1f)
            }
        }
    }

    var soundAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    val soundPool = SoundPool
        .Builder()
        .setMaxStreams(3)
        .setAudioAttributes(soundAttributes)
        .build()


    val bubble = Player(R.raw.bubble, 0.8f)
    val knock = Player(R.raw.knock, 0.5f)
    val pong = Player(R.raw.pong, 1f)
    val transporter = Player(R.raw.transporter, 1f)
    val applause = Player(R.raw.applause, 0.5f)

    var systemVolume = 1f

    val enabled = BooleanPreference("sound-enabled", true)

    fun init() {
        listOf(bubble, knock, pong, transporter, applause).forEach {
            Logger.info(this, "init sound")
        }
    }
}