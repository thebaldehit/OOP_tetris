package com.example.tetris.figures

import android.graphics.Color

class Rlka : Figure() {
    override var figureShape: Array<Array<Int>> = arrayOf(
        arrayOf(1, 0, 0),
        arrayOf(1, 1, 1),
        arrayOf(0, 0, 0)
    )
    override val color: Int = Color.BLUE
    override var startPos: Int = 4
}