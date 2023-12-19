package com.example.tetris

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MainCanvas(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val grid = 20f
    private var blocks: MutableList<MutableList<Any>> = mutableListOf() // TODO change Any
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawField(canvas)
    }

    private fun drawField(canvas: Canvas) {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        var currentX = 0f
        var currentY = 0f
        for (row in blocks) {
            for (col in row) {
                canvas.drawRect(currentX, currentY, currentX + grid, currentY + grid, paint)
                currentX += grid
            }
            currentY += grid
        }
    }

    fun invalidateCanvas(list: MutableList<MutableList<Any>>) {
        blocks = list
        invalidate()
    }
}