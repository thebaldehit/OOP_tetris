package com.example.tetris

import android.content.Context
import android.media.MediaPlayer

object MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun startMusic(context: Context, resourceId: Int) {
        stopMusic()
        mediaPlayer = MediaPlayer.create(context, resourceId)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    private fun stopMusic() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun resumeMusic() {
        mediaPlayer?.start()
    }

    fun isMusicPlaying(): Boolean {
        return mediaPlayer != null
    }
}