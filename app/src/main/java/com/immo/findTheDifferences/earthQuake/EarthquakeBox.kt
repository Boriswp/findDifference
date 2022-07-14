package com.immo.findTheDifferences.earthQuake

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp

@Stable
class EarthquakeState {
    var isShaking by mutableStateOf(false)
    var earthquakeDuration by mutableStateOf(500L)
    var shakesPerSecond by mutableStateOf(10)
    var shakeForce by mutableStateOf(20)
}

@Composable
fun EarthquakeBox(
    onEarthquakeFinished: () -> Unit = {},
    content: @Composable IEarthquakeScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    val state = remember { EarthquakeState() }
    val scope = remember { EarthquakeScope(state = state) }
    val mover = remember { EarthquakeMover() }
    val controller = remember {
        EarthquakeController(
            scope = coroutineScope,
            mover = mover,
            onEarthquakeFinished = {
                state.isShaking = false
                onEarthquakeFinished()
            }
        )
    }

    LaunchedEffect(state.isShaking) {
        if (state.isShaking) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            controller.startShaking(
                earthquakeDuration = state.earthquakeDuration,
                shakeDuration = 1000L / state.shakesPerSecond,
                shakeForce = state.shakeForce
            )
        } else {
            controller.stopShaking()
        }
    }

    Box(
        modifier = Modifier
            .alpha(mover.alpha.value)
            .offset(mover.x.value.dp, mover.y.value.dp),
        //.rotate(mover.rotation.value)
        //.padding(state.shakeForce.dp)
    ) {
        scope.content()
    }
}