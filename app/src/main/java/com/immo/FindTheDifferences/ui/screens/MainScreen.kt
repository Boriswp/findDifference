package com.immo.FindTheDifferences.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.immo.FindTheDifferences.MainActivityViewModel


@Composable
fun MainScreen(viewModel: MainActivityViewModel) {
    val isGameScreen = rememberSaveable {
        mutableStateOf(false)
    }
    val activity = (LocalContext.current as? Activity)
    BackHandler {
        if (!isGameScreen.value) {
            activity?.finish()
        } else {
            isGameScreen.value = !isGameScreen.value
        }
    }
    if (!isGameScreen.value) {
        GreetingScreen(isGameScreen)
    } else {
        GameScreen(isGameScreen,viewModel)
    }
}
