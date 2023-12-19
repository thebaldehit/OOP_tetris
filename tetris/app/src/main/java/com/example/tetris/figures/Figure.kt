package com.example.tetris.figures

open class Figure() {
    open val figureShape: Array<Array<Int>> = arrayOf()
    open val color: Int = 0
    open val startPos: Int = 0

    open fun rotate() {

    }
}