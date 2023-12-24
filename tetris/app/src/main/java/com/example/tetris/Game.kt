package com.example.tetris

import com.example.tetris.constance.Constance
import com.example.tetris.figures.Figure
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.random.Random

class Game {
    private var rows = 0
    private var score = 0
    private var rowCollected = 0
    private var speed: Long = 500
    private var isGame = true

    private lateinit var figure: Figure
    private lateinit var nextFigure: Figure
    private var gameField: MutableList<MutableList<Block?>> = mutableListOf()

    private lateinit var stopGame: (score: Int) -> Unit
    private lateinit var changeRowsCount: (rows: Int) -> Unit
    private lateinit var changeScoreView: (score: Int) -> Unit
    private lateinit var invalidateCanvas: (list: MutableList<MutableList<Block?>>) -> Unit
    private lateinit var changeNextFigureView: (list: MutableList<MutableList<Block?>>) -> Unit

    fun setInvalidateCanvas(invalidateFn: (list: MutableList<MutableList<Block?>>) -> Unit) {
        invalidateCanvas = invalidateFn
    }

    fun setChangeNextFigure(fn: (list: MutableList<MutableList<Block?>>) -> Unit) {
        changeNextFigureView = fn
    }

    fun setChangeScore(fn: (score: Int) -> Unit) {
        changeScoreView = fn
    }

    fun setChangeRowCount(fn: (rows: Int) -> Unit) {
        changeRowsCount = fn
    }

    fun setStopGame(fn: (score: Int) -> Unit) {
        stopGame = fn
    }

    private fun isBlockYExist(y: Int) : Boolean {
        return y <= Constance.FIELD_ROWS - 1
    }

    private fun isBlockXExist(x: Int) : Boolean {
        return x >= 0 && x <= Constance.FIELD_COLS - 1
    }

    private fun canFigureFall() : Boolean {
        for (row in gameField.indices) {
            for (col in gameField[row].indices) {
                val block = gameField[row][col]
                if (block != null && block.isMove) {
                    if (!isBlockYExist(row + 1)) return false
                    val nextBlock = gameField[row + 1][col]
                    if (nextBlock != null && !nextBlock.isMove) return false
                }
            }
        }
        return true
    }

    private fun canFigureMoveToSide(side: Int) : Boolean {
        for (row in gameField.indices) {
            for (col in gameField[row].indices) {
                val block = gameField[row][col]
                if (block != null && block.isMove) {
                    if (!isBlockXExist(col + side)) return false
                    val nextSideBlock = gameField[row][col + side]
                    if (nextSideBlock != null && !nextSideBlock.isMove) return false
                }
            }
        }
        return true
    }

    fun startGame() {
        thread {
            while (isGame) {
                invalidateCanvas(gameField)
                TimeUnit.MILLISECONDS.sleep(speed)
                if (canFigureFall()) moveFigure()
                else {
                    stopFigure()
                    deleteCollectedRow()
                    addScore()
                    if (!checkGameOver()) {
                        generateNextFigure()
                        showNextFigure()
                        placeFigure()
                    }
                }
            }
        }
    }

    fun pauseGame() {
        isGame = false
    }

    fun resumeGame() {
        isGame = true
        startGame()
    }

    fun initGame() {
        fillGameField()
        generateStartFigures()
        showNextFigure()
        placeFigure()
    }

    private fun checkGameOver() : Boolean {
        for (col in gameField[3]) {
            if (col != null) {
                isGame = false
                stopGame(score)
                return true
            }
        }
        return false
    }

    private fun getRandomFigure(): Figure {
        val randomNumber = Random.nextInt(7) + 1
        val figureConstructor = Constance.FIGURE_CONSTRUCTORS[randomNumber]
        return figureConstructor!!.invoke()
    }

    private fun generateStartFigures() {
        nextFigure = getRandomFigure()
        figure = getRandomFigure()
    }

    private fun generateNextFigure() {
        figure = nextFigure
        nextFigure = getRandomFigure()
    }

    private fun showNextFigure() {
        val blocks: MutableList<MutableList<Block?>> = mutableListOf()
        for (row in nextFigure.figureShape) {
            val blocksRow = mutableListOf<Block?>()
            for (col in row) {
                if (col == 1) blocksRow.add(Block(nextFigure.color))
                else blocksRow.add(null)
            }
            blocks.add(blocksRow)
        }
        changeNextFigureView(blocks)
    }

    private fun moveFigure() {
        for (row in gameField.size - 1 downTo 0) {
            for (col in gameField[row].size - 1 downTo 0) {
                val block = gameField[row][col]
                if (block != null && block.isMove) {
                    gameField[row + 1][col] = gameField[row][col]
                    gameField[row][col] = null
                }
            }
        }
        figure.currentRow++
    }

    fun moveFigureLeft() {
        if (!canFigureMoveToSide(Constance.LEFT)) return
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
        if (!canFigureMoveToSide(Constance.RIGHT)) return
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
        moveFigure()
        invalidateCanvas(gameField)
    }

    private fun clearFigures() {
        for (row in gameField.indices) {
            for (col in gameField[row].indices) {
                val block = gameField[row][col]
                if (block != null && block.isMove) gameField[row][col] = null
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

    private fun tryRotateFigure() {
        figure.rotate()
        for (row in figure.figureShape.indices) {
            for (col in figure.figureShape[row].indices) {
                if (figure.figureShape[row][col] == 1) {
                    val curY = figure.currentRow + row
                    val curX = figure.startPos + col
                    if (!isBlockXExist(curX) || !isBlockYExist(curY) || gameField[curY][curX] != null) {
                        for (i in 0..2) figure.rotate()
                        return
                    }
                }
            }
        }
    }

    private fun stopFigure() {
        for (row in gameField) {
            for (col in row) {
                if (col != null && col.isMove) col.isMove = false
            }
        }
    }

    fun rotateFigure() {
        clearFigures()
        tryRotateFigure()
        placeFigure()
        invalidateCanvas(gameField)
    }

    private fun increaseSpeed() {
        val newSpeed = speed * 0.7
        speed = newSpeed.toLong()
    }

    private fun addScore() {
        if (rowCollected == 0) return
        score += Constance.ROWS_COST[rowCollected]!!
        addRowsCount(rowCollected)
        rowCollected = 0
        changeScoreView(score)
    }

    private fun addRowsCount(rowsCount: Int) {
        rows += rowsCount
        if (rows % 10 == 0) increaseSpeed()
        changeRowsCount(rows)
    }

    private fun fillGameField() {
        gameField = mutableListOf()
        for (i in 0..Constance.FIELD_ROWS) {
            val row = mutableListOf<Block?>()
            for (j in 0..9) row.add(null)
            gameField.add(row)
        }
    }

    private fun deleteCollectedRow() {
        for (row in gameField.size - 1 downTo 0) {
            var notNullBlockCount = 0
            for (col in gameField[row].indices) {
                if (gameField[row][col] != null) notNullBlockCount++
            }
            if (notNullBlockCount == Constance.FIELD_COLS) {
                gameField.removeAt(row)
                val newRow: MutableList<Block?> = mutableListOf()
                for (i in 0..<Constance.FIELD_COLS) newRow.add(null)
                gameField.add(0, newRow)
                rowCollected++
                deleteCollectedRow()
            }
        }
    }
}