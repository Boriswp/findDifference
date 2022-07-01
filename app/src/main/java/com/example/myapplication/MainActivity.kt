package com.example.myapplication

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
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
    if (!isGameScreen.value) {
        GreetingScreen(isGameScreen)
    } else {
        GameScreen(isGameScreen)
    }
}

@Composable
fun GreetingScreen(isGameScreen: MutableState<Boolean>) {
        Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
            Text(text = "Здесь будет гифка")
        }
        Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.BottomCenter) {
            TextButton(onClick = { isGameScreen.value = true }) {
                Text(text = "Играть")
            }
        }
}

@Composable
fun GameScreen(isGameScreen: MutableState<Boolean>) {
    Column(modifier = Modifier.fillMaxSize()) {
        LinearProgressIndicator(progress = 0.5f)
        Box() {
        }
    }
}
