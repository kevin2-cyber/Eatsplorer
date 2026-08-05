package com.kimikevin.eatsplorer

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.core.content.edit
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kimikevin.eatsplorer.view.screens.MainScreen
import com.kimikevin.eatsplorer.view.theme.EatsplorerTheme
import com.kimikevin.eatsplorer.viewmodel.SplashViewModel

class MainActivity : ComponentActivity() {
    companion object {
        const val PREFS_NAME = "eatsplorer_prefs"
        const val KEY_ONBOARDING_COMPLETE = "onboarding_complete"
    }

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = TRANSPARENT,
                darkScrim = TRANSPARENT,
            )
        )

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        var onboardingComplete by mutableStateOf(prefs.getBoolean(KEY_ONBOARDING_COMPLETE, false))

        if (!onboardingComplete) {
            splashScreen.setKeepOnScreenCondition {
                !splashViewModel.isDataReady.value
            }
        }

        setContent {
            EatsplorerTheme {
                MainScreen(
                    onboardingComplete = onboardingComplete,
                    onOnboardingFinished = {
                        prefs.edit { putBoolean(KEY_ONBOARDING_COMPLETE, true) }
                        onboardingComplete = true
                    }
                )
            }
        }
    }
}
