package com.example.words.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.primaryContainerDark
import com.example.compose.primaryDark
import com.example.compose.primaryLight


@Composable
fun CirclesAppName(
    text: String,
    modifier: Modifier = Modifier,
    color: Color
){
    Box(
        modifier = Modifier
        .size(180.dp)
    ) {
        CircleNameApp(
            text = "",
            color = color,
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.TopStart)
        )
        CircleNameApp(
            text = text,
            color = color,
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center)
        )
        CircleNameApp(
            text = "",
            color = color,
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun CircleNameApp(
    text: String,
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color = color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CirclesAppName(text = "Words", color = Color.Black)
}