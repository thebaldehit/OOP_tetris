package com.example.tetris

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class MainCanvas(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val grid = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, Resources.getSystem().displayMetrics)
    private var blocks: MutableList<MutableList<Block?>> = mutableListOf()
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
                if (col != null) {
                    canvas.drawRect(currentX, currentY, currentX + grid, currentY + grid, paint)
                }
                currentX += grid
            }
            currentX = 0f
            currentY += grid
        }
    }

    fun invalidateCanvas(list: MutableList<MutableList<Block?>>) {
        blocks = list
        invalidate()
    }
}