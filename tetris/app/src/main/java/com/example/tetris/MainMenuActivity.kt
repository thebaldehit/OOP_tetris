package com.example.tetris

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tetris.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    private lateinit var bindingClass : ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.play.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        bindingClass.soundMain.setOnClickListener { MusicPlayer.changeSound() }
        MusicPlayer.startMusic(this, R.raw.vstup)
    }

    override fun onResume() {
        super.onResume()
        showScore()
        MusicPlayer.startMusic(this, R.raw.vstup)
    }

    override fun onPause() {
        super.onPause()
        MusicPlayer.pauseMusic()
    }

    @SuppressLint("SetTextI18n")
    private fun showScore() {
        val sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE)
        val userScore = sharedPreferences.getInt("score", 0)
        bindingClass.record.text = "Your record: $userScore"
    }
}