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
                    paint.style = Paint.Style.FILL
                    paint.color = col.color
                    canvas.drawRect(currentX, currentY, currentX + Constance.GRID, currentY + Constance.GRID, paint)
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = 10f
                    paint.color = Color.BLACK
                    canvas.drawRect(currentX, currentY, currentX + Constance.GRID, currentY + Constance.GRID, paint)
                }
                currentX += Constance.GRID
            }
            currentX = 0f
            currentY += Constance.GRID
        }
    }

    fun setStartRow(row: Int) {
        startRow = row
    }

    fun invalidateCanvas(list: MutableList<MutableList<Block?>>) {
        blocks = list
        invalidate()
    }
}