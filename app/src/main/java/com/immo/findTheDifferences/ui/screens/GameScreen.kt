package com.immo.findTheDifferences.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.immo.findTheDifferences.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.immo.findTheDifferences.Const
import com.immo.findTheDifferences.MainActivityViewModel
import com.immo.findTheDifferences.UserState
import com.immo.findTheDifferences.earthQuake.EarthquakeBox
import com.immo.findTheDifferences.ui.components.TimeIndicator
import com.immo.findTheDifferences.ui.dialogs.YouLoseDialog
import com.immo.findTheDifferences.ui.dialogs.YouWinDialog
import com.immo.findTheDifferences.localization.*
import com.immo.findTheDifferences.localization.Vocabulary.localization
import com.immo.findTheDifferences.ui.dialogs.EndLvlsDialog
import com.immo.findTheDifferences.ui.theme.Typography

import kotlin.random.Random

@Composable
fun GameScreen(isGameScreen: MutableState<Boolean>, viewModel: MainActivityViewModel) {
    val currState = remember {
        mutableStateOf<UserState>(UserState.Initial)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        if (currState.value == UserState.Lose) {
            YouLoseDialog(isGameScreen = isGameScreen)
        }

        if (currState.value == UserState.Win) {
            EndLvlsDialog(isGameScreen = isGameScreen)
        }

        TimeIndicator(currState)

        EarthquakeBox(
            onEarthquakeFinished = {
                println("finished")
            }
        ) {
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
                painter = painterResource(id = R.drawable.background_image),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.clickable { if (!isShaking) startShaking() }
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
                        .clickable { if (!isShaking) currState.value = UserState.Win }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = "${localization.game_lvl_finished()} ${0} ${localization.game_lvl_of()} ${1}",
                    textAlign = TextAlign.Center,
                    style = Typography.h1,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier
                        .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
                        .fillMaxWidth()
                )
            }
        }
    }
}