package com.example.visprog_week5.ui.model

import kotlin.random.Random

data class Soal1UiState (
    var numberToGuess: Int = Random.nextInt(1, 11),
    var guess: Int = 0,
    var score: Int = 0,
    var isGameOver: Boolean = false
)