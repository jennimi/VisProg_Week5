package com.example.visprog_week5.ui.model

data class Soal2UiState (
    var totalSKS: Int = 0,
    var totalScore: Double = 0.0,
    var ipk: Double = 0.0,
    var listOfCourses: List<Course> = emptyList()
)

data class Course (
    var name: String,
    var sks: Int,
    var score: Double,
    var finScore: Double
)
