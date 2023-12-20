package com.example.tetris.figures

import android.graphics.Color

class Lka : Figure() {
    override var figureShape: Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 1),
        arrayOf(1, 1, 1),
        arrayOf(0, 0, 0)
    )
    override val color: Int = Color.rgb(4, 155, 174)
    override var startPos: Int = 4
}