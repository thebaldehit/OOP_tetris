package com.example.tetris.constance

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
    const val FIELD_ROWS = 23
    const val FIELD_COLS = 10
    const val RIGHT = 1
    const val LEFT = -1
}