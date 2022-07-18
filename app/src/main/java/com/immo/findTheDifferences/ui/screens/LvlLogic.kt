package com.immo.findTheDifferences.ui.screens

import android.service.autofill.SaveCallback
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.immo.findTheDifferences.Const
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.UserState
import com.immo.findTheDifferences.earthQuake.EarthquakeBox
import com.immo.findTheDifferences.localization.Vocabulary.localization
import com.immo.findTheDifferences.localization.game_lvl_finished
import com.immo.findTheDifferences.localization.game_lvl_of
import com.immo.findTheDifferences.remote.FilesData
import com.immo.findTheDifferences.ui.components.TimeIndicator
import com.immo.findTheDifferences.ui.theme.Typography
import kotlin.random.Random

@Composable
fun LvlLogic(
    currState: MutableState<UserState>,
    currLvlsData: List<FilesData>,
    listIndexes: List<Int>,
    totalLvls: Int,
    currLvl: MutableState<Int>,
    tapCounts: MutableState<Int>,
    hint: MutableState<Boolean>,
    saveCallback: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val configuration = LocalConfiguration.current
    var bottomHeight by remember {
        mutableStateOf(0)
    }

    var ready by remember {
        mutableStateOf(false)
    }

    var height by remember {
        mutableStateOf(0)
    }

    if (ready) {
        TimeIndicator(currState)
    }

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

        SubcomposeAsyncImage(
            model = Const.BASE_URL_FOR_PICTURES + currLvlsData[listIndexes[currLvl.value]].picture_background,
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                ready = true
                SubcomposeAsyncImageContent()
            }
        }

        fun badTap() {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            if (!isShaking) {
                tapCounts.value++
                if (tapCounts.value < Const.loseTapCounts) {
                    startShaking()
                } else {
                    currState.value = UserState.Lose
                }
            }
        }

        SubcomposeAsyncImage(
            model = Const.BASE_URL_FOR_PICTURES + currLvlsData[listIndexes[currLvl.value]].picture_foreground,
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { badTap() },
                        onLongPress = { badTap() },
                        onDoubleTap = { badTap() })
                }
                .fillMaxSize(),
            alpha = imageAlpha
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                ready = true
                SubcomposeAsyncImageContent()
            }
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
        if (hint.value) {
            Image(
                painter = painterResource(id = R.drawable.ic_rectangle),
                contentDescription = "hint",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(
                        start = (currLvlsData[listIndexes[currLvl.value]].padding_left * start).dp,
                        top = ((currLvlsData[listIndexes[currLvl.value]].padding_top * top).dp)
                    )
                    .height(currLvlsData[listIndexes[currLvl.value]].object_height.dp)
                    .width(currLvlsData[listIndexes[currLvl.value]].object_width.dp)
            )
        }

        Box(
            Modifier
                .padding(
                    start = (currLvlsData[listIndexes[currLvl.value]].padding_left * start).dp,
                    top = ((currLvlsData[listIndexes[currLvl.value]].padding_top * top).dp)
                )
                .height(currLvlsData[listIndexes[currLvl.value]].object_height.dp)
                .width(currLvlsData[listIndexes[currLvl.value]].object_width.dp)
                .clickable {
                    if (!isShaking) {
                        saveCallback()
                    }
                }
        )
    }
}