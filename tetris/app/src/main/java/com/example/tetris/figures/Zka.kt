package com.example.tetris.figures

import android.graphics.Color

class Zka : Figure() {
    override var figureShape: Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 0),
        arrayOf(1, 1, 0),
        arrayOf(0, 1, 1)
    )
    override val color: Int = Color.rgb(197, 100, 16)
    override var startPos: Int = 4

    override fun rotate() {
        val firstSecond = figureShape[0][1]
        figureShape[0][1] = figureShape[2][2]
        figureShape[2][2] = firstSecond
        val thirdSecond = figureShape[2][1]
        figureShape[2][1] = figureShape[2][0]
        figureShape[2][0] = thirdSecond
    }
}