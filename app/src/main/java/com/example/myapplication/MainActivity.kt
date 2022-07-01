package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.*
import kotlin.math.abs


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        MainScreen()
                        AndroidView(
                            modifier = Modifier.fillMaxWidth(),
                            factory = { context ->
                                AdView(context).apply {
                                    setAdSize(AdSize.BANNER)
                                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                                    loadAd(AdRequest.Builder().build())
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MainScreen() {
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
        GameScreen(isGameScreen)
    }
}

@Composable
fun GreetingScreen(isGameScreen: MutableState<Boolean>) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Здесь будет гифка")
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            TextButton(onClick = { isGameScreen.value = true }) {
                Text(text = "Играть")
            }
        }
    }
}


@Composable
fun GameScreen(isGameScreen: MutableState<Boolean>) {
    val youWin = rememberSaveable {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        val progressAnimDuration = 1500

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp), // Rounded edges
            progress = animateFloatAsState(
                targetValue = 1F,
                animationSpec = tween(
                    durationMillis = progressAnimDuration,
                    easing = FastOutSlowInEasing
                )
            ).value
        )

        Box() {
        }
    }

}
