package com.example.tetris

class Block(setColor: Int) {
    var isMove = true
    var color: Int = 0
    init {
        color = setColor
    }
}