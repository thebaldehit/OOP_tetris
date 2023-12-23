package com.example.tetris

import android.content.Context
import android.media.MediaPlayer

object MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private var sound = true

    fun startMusic(context: Context, resourceId: Int) {
        stopMusic()
        mediaPlayer = MediaPlayer.create(context, resourceId)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
        if (!sound) pauseMusic()
    }

    private fun stopMusic() {
        mediaPlayer?.apply {
            if (isPlaying) stop()
            release()
        }
        mediaPlayer = null
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun resumeMusic() {
        if (sound) mediaPlayer?.start()
    }

    fun changeSound() {
        if (sound) {
            sound = false
            pauseMusic()
        } else {
            sound = true
            resumeMusic()
        }
    }
}