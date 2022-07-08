package com.immo.findTheDifferences.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.immo.findTheDifferences.Const
import com.immo.findTheDifferences.MainActivityViewModel
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.UserState
import com.immo.findTheDifferences.earthQuake.EarthquakeBox
import com.immo.findTheDifferences.localization.Vocabulary.localization
import com.immo.findTheDifferences.localization.game_lvl_finished
import com.immo.findTheDifferences.localization.game_lvl_of
import com.immo.findTheDifferences.ui.components.TimeIndicator
import com.immo.findTheDifferences.ui.dialogs.EndLvlsDialog
import com.immo.findTheDifferences.ui.dialogs.YouLoseDialog
import com.immo.findTheDifferences.ui.dialogs.YouWinDialog
import com.immo.findTheDifferences.ui.theme.Typography
import kotlin.random.Random

@Composable
fun GameScreen(isGameScreen: MutableState<Boolean>, viewModel: MainActivityViewModel) {

    val currState = remember {
        mutableStateOf<UserState>(UserState.Initial)
    }
    val tapCounts = rememberSaveable {
        mutableStateOf(0)
    }
    val currLvl = rememberSaveable {
        mutableStateOf(0)
    }
    val lvlList = arrayListOf(R.drawable.img_1, R.drawable.img_2, R.drawable.img_3)
    val alphaList = arrayListOf(R.drawable.img_1_1, R.drawable.img_2_2, R.drawable.img_3_3)
    val paddingTop = arrayListOf(485, 500, 370)
    val paddingLeft = arrayListOf(225, 30, 220)
    val sizeX = arrayListOf(80, 46, 50)
    val sizeY = arrayListOf(45, 70, 50)
    val listIndexes by remember { mutableStateOf((0 until lvlList.size).shuffled()) }

    Column(modifier = Modifier.fillMaxSize()) {

        if (currState.value == UserState.Lose) {
            YouLoseDialog(isGameScreen = isGameScreen) {
                tapCounts.value = 0
                currState.value = UserState.Initial
            }
        }

        if (currState.value == UserState.Win) {
            if (currLvl.value < lvlList.size - 1) {
                YouWinDialog(isGameScreen = isGameScreen) {
                    currLvl.value++
                    tapCounts.value = 0
                    currState.value = UserState.Initial
                }
            } else {
                EndLvlsDialog(isGameScreen = isGameScreen)
            }
        }


        LvlLogic(
            currState = currState,
            lvlList[listIndexes[currLvl.value]],
            alphaList[listIndexes[currLvl.value]],
            paddingTop[listIndexes[currLvl.value]],
            paddingLeft[listIndexes[currLvl.value]],
            sizeX[listIndexes[currLvl.value]],
            sizeY[listIndexes[currLvl.value]],
            lvlList.size,
            currLvl,
            tapCounts
        )
    }
}

@Composable
fun LvlLogic(
    currState: MutableState<UserState>,
    currLvlImage: Int,
    currAlphaLvlImage: Int,
    paddingTop: Int,
    paddingLeft: Int,
    sizeX: Int,
    sizeY: Int,
    totalLvls: Int,
    currLvl: MutableState<Int>,
    tapCounts: MutableState<Int>,
) {
    val haptic = LocalHapticFeedback.current
    val configuration = LocalConfiguration.current
    var bottomHeight by remember {
        mutableStateOf(0)
    }

    var imgHeight by remember {
        mutableStateOf(0)
    }

    var height by remember {
        mutableStateOf(0)
    }


    TimeIndicator(currState)

    EarthquakeBox(
        onEarthquakeFinished = {
            println("finished")
        }
    ) {
        val interactionSource = remember { MutableInteractionSource() }


        if (currState.value == UserState.Initial) {
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
                painter = painterResource(id = currLvlImage),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )
            Image(
                painter = painterResource(id = currAlphaLvlImage),
                alpha = imageAlpha,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clickable(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (!isShaking) {
                                tapCounts.value++
                                if (tapCounts.value < Const.loseTapCounts) {
                                    startShaking()
                                } else {
                                    currState.value = UserState.Lose
                                }
                            }
                        },
                        indication = null,
                        interactionSource = interactionSource
                    )
                    .fillMaxSize()
            )
        } else {
            Image(
                painter = painterResource(id = currAlphaLvlImage),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()

            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "${localization.game_lvl_finished()} ${currLvl.value} ${localization.game_lvl_of()} $totalLvls",
                textAlign = TextAlign.Center,
                style = Typography.h2,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier
                    .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
                    .fillMaxWidth()
                    .onPlaced {
                        bottomHeight = it.size.height
                    }
            )
        }

        height = configuration.screenHeightDp - bottomHeight
        val top =
            if (Const.imageHeight < height) Const.imageHeight / height else height / Const.imageHeight
        val start =
            if (Const.imageWidth < configuration.screenWidthDp) Const.imageWidth / configuration.screenWidthDp else configuration.screenWidthDp / Const.imageWidth
        Box(
            Modifier
                .padding(
                    start = (paddingLeft * start).dp,
                    top = ((paddingTop * top).dp)
                )
                .height(sizeY.dp)
                .width(sizeX.dp)
                .clickable { if (!isShaking) currState.value = UserState.Win }
        )

    }
}