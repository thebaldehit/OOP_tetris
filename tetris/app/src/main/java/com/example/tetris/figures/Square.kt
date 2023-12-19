package com.example.tetris.figures

import android.graphics.Color

class Square : Figure() {
    override val figureShape: Array<Array<Int>> = arrayOf(
        arrayOf(1, 1),
        arrayOf(1, 1)
    )
    override val color: Int = Color.GREEN
}