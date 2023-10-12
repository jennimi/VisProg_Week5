package com.example.visprog_week5.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.visprog_week5.ui.model.Soal1UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class Soal1ViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(Soal1UiState())
    val uiState: StateFlow<Soal1UiState> = _uiState.asStateFlow()

    private fun newNumberToGuess() : Int{
        return Random.nextInt(1, 11)
    }

    fun isGameOver(userGuess: Int) {

        if (_uiState.value.numberToGuess == userGuess) { //if correct, score adds, resets guess, create new number to guess, check if score = 3
            val scoreAdded = _uiState.value.score + 1

            _uiState.update { data ->
                data.copy(score = scoreAdded, guess = 0, numberToGuess = newNumberToGuess())
            }

            if (_uiState.value.score >= 3) {
                _uiState.update { data ->
                    data.copy(isGameOver = true)
                }
            }

        } else { //if wrong, adds guess, checks if guess is 3
            val guessAdded = _uiState.value.guess + 1

            _uiState.update { data ->
                data.copy(guess = guessAdded)
            }

            if (_uiState.value.guess >= 3) {
                _uiState.update { data ->
                    data.copy(isGameOver = true)
                }
            }
        }

    }

    fun restartGame() {
        _uiState.update { data ->
            data.copy(score = 0, guess = 0, numberToGuess = newNumberToGuess(), isGameOver = false)
        }
    }

}