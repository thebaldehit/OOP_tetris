package com.example.tetris

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.example.tetris.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass : ActivityMainBinding
    private var lButtonPressed = false
    private var rButtonPressed = false
    private var dButtonPressed = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.canvas.setStartRow(3)

        val game = Game()
        game.setInvalidateCanvas(bindingClass.canvas::invalidateCanvas)
        game.setChangeNextFigure(bindingClass.nextFigureCanvas::invalidateCanvas)
        game.setStopGame(::stopGame)
        game.setChangeScore(::addScore)
        game.setChangeRows(::addRows)
        game.initGame()
        game.startGame()

        bindingClass.imageButtonLeft.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!rButtonPressed) lButtonPressed = true
                    thread {
                        while (lButtonPressed) {
                            game.moveFigureLeft()
                            TimeUnit.MILLISECONDS.sleep(100)
                        }
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    lButtonPressed = false
                    true
                }
                else -> false
            }
        }

        bindingClass.imageButtonRight.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!lButtonPressed) rButtonPressed = true
                    thread {
                        while (rButtonPressed) {
                            game.moveFigureRight()
                            TimeUnit.MILLISECONDS.sleep(100)
                        }
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    rButtonPressed = false
                    true
                }
                else -> false
            }
        }

        bindingClass.imageButtonDown.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dButtonPressed = true
                    thread {
                        while (dButtonPressed) {
                            game.moveFigureDown()
                            TimeUnit.MILLISECONDS.sleep(50)
                        }
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    dButtonPressed = false
                    true
                }
                else -> false
            }
        }

        bindingClass.buttonRotate.setOnClickListener { game.rotateFigure() }
        bindingClass.gameOver.setOnClickListener {
            game.restartGame()
            it.visibility = View.GONE
        }
    }

    private fun stopGame(score: Int) {
        runOnUiThread {
            bindingClass.gameOver.text = "${bindingClass.gameOver.text}\nScore: $score\nPress to restart"
            bindingClass.gameOver.visibility = View.VISIBLE
        }
    }

    private fun addScore(score: Int) {
        bindingClass.score.text = score.toString()
    }

    private fun addRows(rows: Int) {
        bindingClass.rows.text = rows.toString()
    }
}