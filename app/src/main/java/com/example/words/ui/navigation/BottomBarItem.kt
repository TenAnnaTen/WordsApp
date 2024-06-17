package com.example.words.ui.navigation

import androidx.annotation.DrawableRes
import com.example.words.R

sealed class BottomBarItem(
    @DrawableRes val iconId: Int,
    val route: String
) {
    object Screen1: BottomBarItem(R.drawable.learning, ScreenRoute.ScreenLearning.name)
    object Screen2: BottomBarItem(R.drawable.list, ScreenRoute.ScreenCategories.name)
    object Screen3: BottomBarItem(R.drawable.settings, ScreenRoute.ScreenSettings.name)
}