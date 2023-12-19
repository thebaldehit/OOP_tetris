package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tetris.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val game = Game()
        game.setInvalidateCanvas(bindingClass.canvas::invalidateCanvas)
        game.startGame()

        bindingClass.imageButtonLeft.setOnClickListener {
            game.moveFigureLeft()
        }

        bindingClass.imageButtonRight.setOnClickListener {
            game.moveFigureRight()
        }

        bindingClass.imageButtonDown.setOnClickListener {
            game.moveFigureDown()
        }

        bindingClass.buttonRotate.setOnClickListener {
            game.rotateFigure()
        }
    }
}