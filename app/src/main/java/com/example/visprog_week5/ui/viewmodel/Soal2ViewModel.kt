package com.example.visprog_week5.ui.viewmodel

import androidx.compose.animation.core.updateTransition
import androidx.lifecycle.ViewModel
import com.example.visprog_week5.ui.model.Course
import com.example.visprog_week5.ui.model.Soal2UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Soal2ViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(Soal2UiState())
    val uiState: StateFlow<Soal2UiState> = _uiState.asStateFlow()

    fun validSKS(sks: String) : Boolean {
        val checksks = sks.toIntOrNull()
        return !(checksks == null || checksks <= 0)
    }

    fun validScore(score: String) : Boolean {
        val checkscore = score.toDoubleOrNull()
        return !(checkscore == null || checkscore < 0.0 || checkscore > 4.0)
    }

    fun validName(name: String) : Boolean {
        return name.isNotEmpty()
    }

    fun addCourse(name: String, sks: String, score:String) {

            val sksInt = sks.toInt()
            val scoreInt = score.toDouble()

            val finScore = sksInt*scoreInt
            val newCourse = Course(name = name, sks = sksInt, score = scoreInt, finScore = finScore)

            val updatedListOfCourses = uiState.value.listOfCourses + newCourse
            val updatedSKS = uiState.value.totalSKS + newCourse.sks
            val updatedScore = uiState.value.totalScore + newCourse.finScore

            _uiState.update { data ->
                data.copy(listOfCourses = updatedListOfCourses, totalSKS = updatedSKS, totalScore = updatedScore)
            }
            updateIPK()

    }

    fun updateIPK() {
        if (uiState.value.totalSKS == 0) {
            _uiState.update { data ->
                data.copy(ipk = 0.0)
            }

        } else {
            val updatedIPK = uiState.value.totalScore/uiState.value.totalSKS

            val formattedIPK = String.format("%.2f", updatedIPK)
            val roundedIPK = formattedIPK.toDouble()

            _uiState.update { data ->
                data.copy(ipk = roundedIPK)
            }
        }
    }

    fun delete(chosenCourse: Course) {
        val currentListOfCourses = uiState.value.listOfCourses
        val indexOfChosenCourse = currentListOfCourses.indexOfFirst { it == chosenCourse }

        val updatedListOfCourses = currentListOfCourses.toMutableList()
        updatedListOfCourses.removeAt(indexOfChosenCourse)

        val updatedSKS = uiState.value.totalSKS - chosenCourse.sks
        val updatedScore = uiState.value.totalScore - chosenCourse.finScore

        _uiState.update { data ->
            data.copy(listOfCourses = updatedListOfCourses, totalSKS = updatedSKS, totalScore = updatedScore)
        }
        updateIPK()
    }

}