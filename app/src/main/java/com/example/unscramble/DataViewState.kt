package com.example.unscramble

data class DataViewState(
    val scrambleWord : String = "",
    val score : Int = 0,
    val countOfWord : Int = 1,
    val gameOver : Boolean = false,
    val userGuessWrong : Boolean = false
)
