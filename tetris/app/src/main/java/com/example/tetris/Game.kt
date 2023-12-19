package com.example.tetris

import android.util.Log
import com.example.tetris.constance.Constance
import com.example.tetris.figures.Figure
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.random.Random

class Game {
    private var isGame = true
    private val gameField: MutableList<MutableList<Block?>> = mutableListOf()
    private lateinit var invalidateCanvas: (list: MutableList<MutableList<Block?>>) -> Unit
    private lateinit var figure: Figure

    private fun fillGameField() {
        for (i in 0..19) {
            val row = mutableListOf<Block?>()
            for (j in 0..9) {
                row.add(null)
            }
            gameField.add(row)
        }
    }

    private fun getNextFigure() {
        val randomNumber = Random.nextInt(7) + 1
        val figureConstructor = Constance.FIGURE_CONSTRUCTORS[randomNumber]
        figure = figureConstructor!!.invoke()
    }

    private fun placeFigure() {
        for (y in figure.figureShape.indices) {
            for (x in figure.figureShape[y].indices) {
                if (figure.figureShape[y][x] == 1) {
                    val block = Block(figure.color)
                    gameField[figure.currentRow + y][figure.startPos + x] = block
                }
            }
        }
    }

    private fun canFigureFall() : Boolean{
        for (row in gameField.indices) {
            for (col in gameField[row].indices) {
                val block = gameField[row][col]
                if (block != null && block.isMove) {
                    if (!isNextBlockExist(row)) return false
                    val nextBlock = gameField[row + 1][col]
                    if (nextBlock == null || nextBlock.isMove) continue
                    else return false
                }
            }
        }
        return true
    }

    private fun isNextBlockExist(y: Int) : Boolean {
        return y + 1 <= Constance.FIELD_ROWS - 1
    }

    private fun stopFigure() {
        for (row in gameField.size - 1 downTo 0) {
            for (col in gameField[row].size - 1 downTo 0) {
                if (gameField[row][col] != null) {
                    if (gameField[row][col]!!.isMove) {
                        gameField[row][col]!!.isMove = false
                    }
                }
            }
        }
    }

    private fun moveFigure() {
        if (canFigureFall()) {
            for (row in gameField.size - 1 downTo 0) {
                for (col in gameField[row].size - 1 downTo 0) {
                    if (gameField[row][col] != null) {
                        if (gameField[row][col]!!.isMove) {
                            gameField[row + 1][col] = gameField[row][col]
                            gameField[row][col] = null
                        }
                    }
                }
            }
        } else {
            stopFigure()
            getNextFigure()
            placeFigure()
        }
    }

    fun startGame() {
        fillGameField()
        getNextFigure()
        placeFigure()
        thread {
            while (isGame) {
                invalidateCanvas(gameField)
                TimeUnit.MILLISECONDS.sleep(100)
                moveFigure()
            }
        }
    }

    fun setInvalidateCanvas(invalidateFn: (list: MutableList<MutableList<Block?>>) -> Unit) {
        invalidateCanvas = invalidateFn
    }
}