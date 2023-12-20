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

    private fun clearFigure() {
        for (row in gameField.indices) {
            for (col in gameField[row].indices) {
                val block = gameField[row][col]
                if (block != null && block.isMove) {
                    gameField[row][col] = null
                }
            }
        }
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
                    if (!isBlockYExist(row + 1)) return false
                    val nextBlock = gameField[row + 1][col]
                    if (nextBlock == null || nextBlock.isMove) continue
                    else return false
                }
            }
        }
        return true
    }

    private fun canFigureMoveSide(side: Int) : Boolean {
        for (row in gameField.indices) {
            for (col in gameField[row].indices) {
                val block = gameField[row][col]
                if (block != null && block.isMove) {
                    if (!isBlockXExist(col + side)) return false
                    val nextSideBlock = gameField[row][col + side]
                    if (nextSideBlock == null || nextSideBlock.isMove) continue
                    else return false
                }
            }
        }
        return true
    }

    private fun tryRotateFigure() {
        figure.rotate()
        for (row in figure.figureShape.indices) {
            for (col in figure.figureShape[row].indices) {
                if (figure.figureShape[row][col] == 1) {
                    val curY = figure.currentRow + row
                    val curX = figure.startPos + col
                    if (!isBlockXExist(curX) || !isBlockYExist(curY) || gameField[curY][curX] != null) {
                        for (i in 0..2) figure.rotate()
                    }
                }
            }
        }
    }

    private fun isBlockYExist(y: Int) : Boolean {
        return y <= Constance.FIELD_ROWS - 1
    }

    private fun isBlockXExist(x: Int) : Boolean {
        return x >= 0 && x <= Constance.FIELD_COLS - 1
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
            figure.currentRow++
        } else {
            stopFigure()
            getNextFigure()
            placeFigure()
        }
    }

    fun moveFigureLeft() {
        if (!canFigureMoveSide(Constance.LEFT)) return
        for (row in gameField.size - 1 downTo 0) {
            for (col in gameField[row].indices) {
                val block = gameField[row][col]
                if (block != null && block.isMove) {
                    gameField[row][col + Constance.LEFT] = block
                    gameField[row][col] = null
                }
            }
        }
        figure.startPos += Constance.LEFT
        invalidateCanvas(gameField)
    }

    fun moveFigureRight() {
        if (!canFigureMoveSide(Constance.RIGHT)) return
        for (row in gameField.size - 1 downTo 0) {
            for (col in gameField[row].size - 1 downTo 0) {
                val block = gameField[row][col]
                if (block != null && block.isMove) {
                    gameField[row][col + Constance.RIGHT] = block
                    gameField[row][col] = null
                }
            }
        }
        figure.startPos += Constance.RIGHT
        invalidateCanvas(gameField)
    }

    fun moveFigureDown() {
        if (!canFigureFall()) return
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
        figure.currentRow++
        invalidateCanvas(gameField)
    }

    fun rotateFigure() {
        clearFigure()
        tryRotateFigure()
        placeFigure()
        invalidateCanvas(gameField)
    }

    fun startGame() {
        fillGameField()
        getNextFigure()
        placeFigure()
        thread {
            while (isGame) {
                invalidateCanvas(gameField)
                TimeUnit.MILLISECONDS.sleep(300)
                moveFigure()
            }
        }
    }

    fun setInvalidateCanvas(invalidateFn: (list: MutableList<MutableList<Block?>>) -> Unit) {
        invalidateCanvas = invalidateFn
    }
}