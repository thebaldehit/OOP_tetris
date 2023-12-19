package com.example.tetris.figures

import android.graphics.Color

class Stick : Figure() {
    override val figureShape: Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(1, 1, 1, 1),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )
    override val color: Int = Color.RED
}