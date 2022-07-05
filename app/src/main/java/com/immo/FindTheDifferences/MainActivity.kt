package com.immo.FindTheDifferences

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.immo.FindTheDifferences.ui.theme.MyApplicationTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlin.random.Random


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
    val currState = remember {
        mutableStateOf<UserState>(UserState.Initial)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        if (currState.value == UserState.Lose) {
            YouLoseDialog(isGameScreen = isGameScreen)
        }

        if (currState.value == UserState.Win) {
            YouWinDialog(isGameScreen = isGameScreen)
        }

        MyIndicator(currState)

        Box {
            val isReversed by remember {
                mutableStateOf(Random.nextBoolean())
            }
            var targetValue by remember { mutableStateOf(if (isReversed) 1f else 0f) }
            val imageAlpha: Float by animateFloatAsState(
                targetValue = targetValue,
                animationSpec = tween(
                    durationMillis = 7000,
                    delayMillis = 2000,
                    easing = LinearEasing,
                )
            )

            SideEffect {
                targetValue = if (isReversed) 0f else 1f
            }

            Image(
                painter = painterResource(id = R.drawable.background_image),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Box(
                Modifier
                    .padding(100.dp, 300.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.alpha_image),
                    alpha = imageAlpha,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clickable { currState.value = UserState.Win }
                )
            }
        }
    }
}


@Composable
fun YouWinDialog(isGameScreen: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            isGameScreen.value = false
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ты Выиграл!",
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        isGameScreen.value = false
                    }, modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(40.dp)
                ) {
                    Text("В меню")
                }

                Button(
                    onClick = {
                    }, modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(40.dp)
                ) {
                    Text("Далее")
                }
            }
        },
        buttons = {

        }, shape = RoundedCornerShape(15.dp)
    )
}

@Composable
fun YouLoseDialog(isGameScreen: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            isGameScreen.value = false
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ты проиграл!",
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        isGameScreen.value = false
                    }, modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(40.dp)
                ) {
                    Text("В меню")
                }

                Button(
                    onClick = {
                    }, modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(40.dp)
                ) {
                    Text("Повторить")
                }
            }
        },
        buttons = {

        }, shape = RoundedCornerShape(15.dp)
    )
}

@Composable
fun MyIndicator(state: MutableState<UserState>) {
    var targetValue by remember { mutableStateOf(0f) }
    val progressAnimDuration = 12000
    val progressAnimation by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = progressAnimDuration,
            delayMillis = 10,
            easing = LinearEasing
        ),
        finishedListener = {
            if (state.value != UserState.Win) {
                state.value = UserState.Lose
            }
        }
    )

    SideEffect { targetValue = 1f }

    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        progress = progressAnimation
    )
}