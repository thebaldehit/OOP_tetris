package com.example.tetris.figures

import android.graphics.Color
class Teshka : Figure() {
    override var figureShape: Array<Array<Int>> = arrayOf(
        arrayOf(0, 1, 0),
        arrayOf(1, 1, 1),
        arrayOf(0, 0, 0)
    )
    override val color: Int = Color.rgb(235, 223, 14)
    override var startPos: Int = 4
}