package com.example.tetris.figures

import android.graphics.Color

class Square : Figure() {
    override var figureShape: Array<Array<Int>> = arrayOf(
        arrayOf(1, 1),
        arrayOf(1, 1)
    )
    override val color: Int = Color.rgb(255, 247, 94)
    override var startPos: Int = 4

    override fun rotate() {
        return
    }
}