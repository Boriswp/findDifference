package com.immo.FindTheDifferences.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.immo.FindTheDifferences.Const
import com.immo.FindTheDifferences.UserState
import com.immo.FindTheDifferences.ui.components.TimeIndicator
import com.immo.FindTheDifferences.ui.dialogs.YouLoseDialog
import com.immo.FindTheDifferences.ui.dialogs.YouWinDialog
import kotlin.random.Random

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

        TimeIndicator(currState)

        Box {
            val isReversed by remember {
                mutableStateOf(Random.nextBoolean())
            }
            var targetValue by remember { mutableStateOf(if (isReversed) Const.targetEnd else Const.targetStart) }
            val imageAlpha: Float by animateFloatAsState(
                targetValue = targetValue,
                animationSpec = tween(
                    durationMillis = Const.objectDurationAnimMS,
                    delayMillis = Const.objectDelayAnimMS,
                    easing = LinearEasing,
                )
            )

            SideEffect {
                targetValue = if (isReversed) Const.targetStart else Const.targetEnd
            }

            Image(
                painter = painterResource(id = com.example.myapplication.R.drawable.background_image),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Box(
                Modifier
                    .padding(100.dp, 300.dp)
            ) {
                Image(painter = painterResource(id = com.example.myapplication.R.drawable.alpha_image),
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