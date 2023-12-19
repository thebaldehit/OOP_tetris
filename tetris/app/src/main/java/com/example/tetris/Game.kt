package com.example.tetris

import android.util.Log
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class Game() {
    private var isGame = true
    private val gameField: MutableList<MutableList<Any>> = mutableListOf()
    private lateinit var invalidateCanvas: (list: MutableList<MutableList<Any>>) -> Unit

    fun setInvalidateCanvas(invalidateFn: (list: MutableList<MutableList<Any>>) -> Unit) {
        invalidateCanvas = invalidateFn
    }



    fun startGame() {

        thread {
            while (isGame) {
                Log.d("MyTag", "Zalupa")
                invalidateCanvas(gameField)
                TimeUnit.SECONDS.sleep(1)
            }
        }

    }
}