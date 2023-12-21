package com.example.tetris.constance

import android.content.res.Resources
import android.util.TypedValue
import com.example.tetris.figures.Lka
import com.example.tetris.figures.Rlka
import com.example.tetris.figures.Ska
import com.example.tetris.figures.Square
import com.example.tetris.figures.Stick
import com.example.tetris.figures.Teshka
import com.example.tetris.figures.Zka

object Constance {
    val FIGURE_CONSTRUCTORS = mapOf(
        1 to { Stick() },
        2 to { Lka() },
        3 to { Rlka() },
        4 to { Square() },
        5 to { Ska() },
        6 to { Zka() },
        7 to { Teshka() }
    )
    val ROWS_COST = mapOf(
        1 to 100,
        2 to 300,
        3 to 700,
        4 to 1500
    )
    const val FIELD_ROWS = 23
    const val FIELD_COLS = 10
    const val RIGHT = 1
    const val LEFT = -1
    val GRID = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25f, Resources.getSystem().displayMetrics)
}