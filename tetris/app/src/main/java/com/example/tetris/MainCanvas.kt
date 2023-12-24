package com.example.tetris

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.tetris.constance.Constance

class MainCanvas(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var blocks: MutableList<MutableList<Block?>> = mutableListOf()
    private val paint = Paint()
    private var startRow = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawField(canvas)
    }

    private fun drawField(canvas: Canvas) {
        var currentX = 0f
        var currentY = 0f
        for (row in startRow until blocks.size) {
            for (col in blocks[row]) {
                if (col != null) {
                    paint.strokeWidth = 10f
                    drawRect(canvas, Paint.Style.FILL, col.color, currentX, currentY, currentX + Constance.GRID, currentY + Constance.GRID)
                    drawRect(canvas, Paint.Style.STROKE, Color.BLACK, currentX, currentY, currentX + Constance.GRID, currentY + Constance.GRID)
                }
                currentX += Constance.GRID
            }
            currentX = 0f
            currentY += Constance.GRID
        }
    }

    private fun drawRect(canvas: Canvas, style: Paint.Style, color: Int, xs: Float, ys: Float, xe: Float, ye: Float) {
        paint.style = style
        paint.color = color
        canvas.drawRect(xs, ys, xe, ye, paint)
    }

    fun setStartRow(row: Int) {
        startRow = row
    }

    fun invalidateCanvas(list: MutableList<MutableList<Block?>>) {
        blocks = list
        invalidate()
    }
}