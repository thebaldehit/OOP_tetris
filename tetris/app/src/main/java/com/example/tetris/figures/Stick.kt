package com.example.tetris.figures

import android.graphics.Color

class Stick : Figure() {
    override var figureShape: Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 1, 0),
        arrayOf(0, 0, 1, 0),
        arrayOf(0, 0, 1, 0),
        arrayOf(0, 0, 1, 0)
    )
    override val color: Int = Color.rgb(215, 45, 206)
    override var startPos: Int = 4

    override fun rotate() {
        val res = Array(4) { Array(4) { 0 } }
        for (row in figureShape.indices) {
            for (col in figureShape[row].indices) {
                res[col][row] = figureShape[row][col]
            }
        }
        figureShape = res
    }
}