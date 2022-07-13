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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.immo.findTheDifferences.*
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.earthQuake.EarthquakeBox
import com.immo.findTheDifferences.localization.Vocabulary.localization
import com.immo.findTheDifferences.localization.game_lvl_finished
import com.immo.findTheDifferences.localization.game_lvl_of
import com.immo.findTheDifferences.remote.FilesData
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
    when (val res = viewModel.lvlDataViewState.collectAsState().value) {
        is LvlDataViewState.Success -> {
            val lvlList = res.response
            val listIndexes by remember { mutableStateOf((lvlList.indices).shuffled()) }

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
                    lvlList.size,
                    currLvl,
                    tapCounts
                )
            }
        }
        is LvlDataViewState.Error -> {
            //TODO
        }
    }
}

@Composable
fun LvlLogic(
    currState: MutableState<UserState>,
    currLvlData: FilesData,
    totalLvls: Int,
    currLvl: MutableState<Int>,
    tapCounts: MutableState<Int>,
) {
    val haptic = LocalHapticFeedback.current
    val configuration = LocalConfiguration.current
    var bottomHeight by remember {
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

            val painterBack = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Const.BASE_URL_FOR_PICTURES + currLvlData.picture_background)
                    .crossfade(200)
                    .build()
            )

            val painterFront = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Const.BASE_URL_FOR_PICTURES + currLvlData.picture_foreground)
                    .crossfade(200)
                    .build()
            )

            Image(
                painter = painterBack,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )
            Image(
                painter = painterFront,
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
                    start = (currLvlData.padding_left * start).dp,
                    top = ((currLvlData.padding_top * top).dp)
                )
                .height(currLvlData.object_height.dp)
                .width(currLvlData.object_width.dp)
                .clickable { if (!isShaking) currState.value = UserState.Win }
        )

    }
}