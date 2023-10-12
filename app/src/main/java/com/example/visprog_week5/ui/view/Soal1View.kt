package com.example.visprog_week5.ui.view

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visprog_week5.ui.viewmodel.Soal1ViewModel

@Composable
fun Soal1View(soal1ViewModel: Soal1ViewModel) {

    val soal1UiState by soal1ViewModel.uiState.collectAsState()
    var userGuessString by rememberSaveable { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text (
            text = "Guess The Number",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Soal1Card(
            guessLeft = soal1UiState.guess,
            numberToGuess = soal1UiState.numberToGuess,
            score = soal1UiState.score,
            userGuess = userGuessString,
            onUserGuessChange = { userGuessString = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        Button (
            onClick = {
                val userGuess = userGuessString.toInt()
                soal1ViewModel.isGameOver(userGuess)
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE91E63) // Pink.
            )
        ){
            Text (
                text = "Submit"
            )
        }

        if (soal1UiState.isGameOver) {
            Soal1Dialog (
                score = soal1UiState.score,
                onPlayAgain = { soal1ViewModel.restartGame() }
            )
        }

    }
}

@Composable
fun Soal1Dialog (
    score: Int,
    onPlayAgain: () -> Unit
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog (
        onDismissRequest = { },
        title = { Text(text = "Welp!") },
        text = { Text(text = "You Scored : $score") },
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = "Exit")
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = "Play Again")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Soal1Card(
    guessLeft: Int,
    numberToGuess: Int,
    score: Int,
    userGuess: String,
    onUserGuessChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Number of Guesses : $guessLeft",
                modifier = Modifier
                    .background(Color(0xFFE91E63), RoundedCornerShape(8.dp)) // Pink.
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                color = Color.White
            )
            Text (
                text = "$numberToGuess",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text (
                text = "From 1 to 10 Guess the Number"
            )
            Text (
                text = "Score : $score"
            )
            OutlinedTextField (
                value = userGuess,
                onValueChange = onUserGuessChange,
                label = { Text(text = "Enter your Number")},
                keyboardOptions = keyboardOptions,
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = Color(0xFFE91E63),
                    focusedIndicatorColor = Color(0xFFE91E63),
                    containerColor = Color(0xFFE2E0EB)
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal1Preview() {
    Soal1View(Soal1ViewModel())
}