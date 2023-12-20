package com.example.tetris.figures

import android.graphics.Color

class Square : Figure() {
    override var figureShape: Array<Array<Int>> = arrayOf(
        arrayOf(1, 1),
        arrayOf(1, 1)
    )
    override val color: Int = Color.rgb(184, 27, 11)
    override var startPos: Int = 4
}