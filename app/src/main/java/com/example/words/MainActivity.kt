package com.example.words

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.words.di.koinModule
import com.example.words.ui.account.settings.ScreenSettingsViewModel
import com.example.words.ui.navigation.Navigation
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MainActivity : ComponentActivity() {

    private val themeViewModel: ScreenSettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            stopKoin()

            startKoin {
                androidContext(applicationContext)
                modules(koinModule)
            }

            val themeState by themeViewModel.uiStateColor.collectAsState()

            AppTheme(
                darkTheme = themeState.dark
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Navigation(navController = navController, themeViewModel, this)
                }
            }
        }
    }
}
