package com.example.tetris.figures
import android.graphics.Color

class Ska : Figure() {
    override val figureShape: Array<Array<Int>> = arrayOf(
        arrayOf(0, 1, 1),
        arrayOf(1, 1, 0),
        arrayOf(0, 0, 0)
    )
    override val color: Int = Color.WHITE
    override val startPos: Int = 4
}