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
    private val grid = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25f, Resources.getSystem().displayMetrics)
    private var blocks: MutableList<MutableList<Block?>> = mutableListOf()
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawField(canvas)
    }

    private fun drawField(canvas: Canvas) {
        var currentX = 0f
        var currentY = 0f
        for (row in 3 until blocks.size) {
            for (col in blocks[row]) {
                if (col != null) {
                    paint.style = Paint.Style.FILL
                    paint.color = col.color
                    canvas.drawRect(currentX, currentY, currentX + grid, currentY + grid, paint)
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = 10f
                    paint.color = Color.BLACK
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