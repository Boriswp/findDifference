package com.immo.findTheDifferences.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.immo.findTheDifferences.MainActivityViewModel


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
        viewModel.prepareLvls()
        GameScreen(isGameScreen, viewModel)
    }
}
