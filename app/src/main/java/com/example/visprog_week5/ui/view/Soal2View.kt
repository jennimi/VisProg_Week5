package com.example.visprog_week5.ui.view

import android.service.autofill.OnClickAction
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visprog_week5.R
import com.example.visprog_week5.ui.model.Course
import com.example.visprog_week5.ui.model.Soal2UiState
import com.example.visprog_week5.ui.viewmodel.Soal2ViewModel

@Composable
fun Soal2View(soal2ViewModel: Soal2ViewModel) {

    val soal2UiState by soal2ViewModel.uiState.collectAsState()
    var sks by rememberSaveable { mutableStateOf("") }
    var score by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var isSKSError by remember { mutableStateOf(false) }
    var isScoreError by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier
            .padding(20.dp),
        content = {
            item {
                Column (
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "Courses",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Total SKS: ${soal2UiState.totalSKS}"
                    )
                    Text(
                        text = "IPK: ${soal2UiState.ipk}"
                    )
                }
            }
            item {
                Column {
                    Row {
                        Column (
                            modifier = Modifier.weight(1f)
                        ) {
                            InputBox (
                                value = sks,
                                onValueChange = { sks = it },
                                text = "SKS",
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                isError = isSKSError,
                                modifier = Modifier,
                                errorMessage = "Enter valid SKS above 0"
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column (
                            modifier = Modifier.weight(1f)
                        ) {
                            InputBox (
                                value = score,
                                onValueChange = { score = it },
                                text = "Score",
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                isError = isScoreError,
                                modifier = Modifier,
                                errorMessage = "Enter score within 0.0 and 4.0"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column (
                            modifier = Modifier.weight(0.8f)
                        ) {
                            InputBox (
                                value = name,
                                onValueChange = { name = it },
                                text = "Name",
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                isError = isNameError,
                                modifier = Modifier,
                                errorMessage = "Enter a name"
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Button (
                            onClick = {
                                isSKSError = !soal2ViewModel.validSKS(sks)
                                isScoreError = !soal2ViewModel.validScore(score)
                                isNameError = !soal2ViewModel.validName(name)

                                if (!isSKSError && !isScoreError && !isNameError) {
                                    soal2ViewModel.addCourse(name, sks, score)
                                }
                            },
                            modifier = Modifier
                                .weight(0.2f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE91E63) // Pink.
                            )
                        ) {
                            Image (
                                painter = painterResource(id = R.drawable.plusicon),
                                contentDescription = "Plus Icon"
                            )
                        }
                    }
                }
            }
            items(soal2UiState.listOfCourses) {
                CourseCard(
                    course = it,
                    onClick = { soal2ViewModel.delete(it) }
                )
            }
        }
    )
}

@Composable
fun CourseCard(course: Course, onClick: () -> Unit) {
    Card (
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
        ) {
            Column {
                Text (
                    text = "Name: ${course.name}",
                    fontWeight = FontWeight.Bold
                )
                Text (
                    text = "SKS: ${course.sks}"
                )
                Text (
                    text = "Score: ${course.score}"
                )
            }
            Button (
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Image (
                    painter = painterResource(id = R.drawable.deleteicon),
                    contentDescription = "Delete Icon",
                    modifier = Modifier
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBox(
    value: String,
    onValueChange: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    isError: Boolean,
    modifier: Modifier,
    errorMessage: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "${text}") },
        keyboardOptions = keyboardOptions,
        isError = isError,
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = Color(0xFFE91E63),
            focusedIndicatorColor = Color(0xFFE91E63),
            containerColor = Color(0xFFFFFFFF)
        )
    )
    if (isError) {
        Text(
            text = "${errorMessage}",
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal2Preview() {
    Soal2View(Soal2ViewModel())
}