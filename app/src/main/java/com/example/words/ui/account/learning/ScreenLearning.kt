package com.example.words.ui.account.learning

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.compose.primaryLight
import com.example.words.R
import com.example.words.ui.account.categories.ViewModelCategories
import com.example.words.ui.account.words.ViewModelWords
import com.example.words.ui.navigation.ScreenRoute
import org.w3c.dom.Text

@Composable
fun ScreenLearning(
    viewModelLearning: ViewModelLearning,
    viewModelCategories: ViewModelCategories,
    viewModelWords: ViewModelWords,
    navController: NavHostController,
    context: Context
){

    var lastDragTime by remember { mutableStateOf(0L) }
    val minSwipeDistance = 10 // Минимальное расстояние для регистрации свайпа в пикселях

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    if (dragAmount > minSwipeDistance) {
                        val currentTime = System.currentTimeMillis()
                        // Добавляем минимальный интервал между свайпами в миллисекундах
                        if (currentTime - lastDragTime > 150) {
                            viewModelLearning.refreshWord(
                                viewModelCategories,
                                viewModelWords,
                                context
                            )
                            lastDragTime = currentTime
                        }
                    }
                }
            }
    ) {
        FlippableCard(
            viewModelLearning,
            viewModelCategories,
            viewModelWords,
            navController,
            context
        )
    }
}

@Composable
private fun FlippableCard(
    viewModelLearning: ViewModelLearning,
    viewModelCategories: ViewModelCategories,
    viewModelWords: ViewModelWords,
    navController: NavHostController,
    context: Context
) {

    val rotation by animateFloatAsState(
        targetValue = if (viewModelLearning.isFlipped.value) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    val uiState = viewModelLearning.uiState.collectAsState()

    viewModelLearning.getLearningWord(
        viewModelCategories,
        viewModelWords,
        context
    )

    Log.d("MyLog", uiState.value.word.toString())

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(300.dp)
            .height(200.dp)
            .clickable { if (uiState.value.word.main_language != null) viewModelLearning.updateIsFlipped() }
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
    ){
        if (uiState.value.word.main_language != null) {
            if (rotation <= 90f) {
                FrontSide(text = uiState.value.word.main_language!!)
            } else {
                BackSide(
                    text1 = uiState.value.word.second_language!!,
                    text2 = uiState.value.word.transcription!!
                )
            }
        } else {
//            FrontSide(text = stringResource(id = R.string.noneWord))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.noneWord),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(id = R.string.noneWordAdd),
                    fontSize = 14.sp,
                    color = primaryLight,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable {
                        navController.navigate(ScreenRoute.ScreenCategories.name)
                    }
                )
            }
        }
    }

}

@Composable
private fun FrontSide(
    text: String
) {
    Card {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
private fun BackSide(
    text1: String,
    text2: String
) {
    Card(
        modifier = Modifier.graphicsLayer {
            rotationY = 180f // Поворачиваем контент задней стороны на 180 градусов
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = text1,
                    fontSize = 24.sp
                )
                Text(
                    text = "[$text2]",
                    fontSize = 18.sp
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewFlippableCard() {
//    BackSide(text1 = "fdtgfds", text2 = "fgtyuiokjhg")
//}