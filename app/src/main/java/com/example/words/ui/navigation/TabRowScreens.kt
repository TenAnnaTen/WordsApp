package com.example.words.ui.navigation

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun TabRowScreen(
    selectedTabIndex: Boolean,
    tabTitles: MutableList<String>,
    onClick: () -> Unit
){

    TabRow(selectedTabIndex = if (selectedTabIndex) 1 else 0) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex,
                onClick = onClick,
                text = { Text(text = title) }
            )
        }
    }
}
