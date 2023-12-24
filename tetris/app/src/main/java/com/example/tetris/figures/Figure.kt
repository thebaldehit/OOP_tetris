package com.example.tetris.figures

open class Figure {
    open var figureShape: Array<Array<Int>> = arrayOf()
    open val color: Int = 0
    open var startPos: Int = 0
    var currentRow = 0

    open fun rotate() {
        val size = figureShape.size - 1
        figureShape = figureShape.mapIndexed { i, row ->
            row.mapIndexed { j, _ ->
                figureShape[j][size-i]
            }.toTypedArray()
        }.toTypedArray()
    }
}