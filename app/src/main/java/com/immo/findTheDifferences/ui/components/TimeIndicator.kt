package com.immo.findTheDifferences.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.immo.findTheDifferences.Const
import com.immo.findTheDifferences.UserState

@Composable
fun TimeIndicator(state: MutableState<UserState>) {
    var targetValue by remember { mutableStateOf(Const.targetStart) }
    val progressAnimation by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = Const.lvlDurationMS,
            easing = LinearEasing
        ),
        finishedListener = {
            if (state.value != UserState.Win) {
                state.value = UserState.Lose
            }
        }
    )

    SideEffect {
        targetValue = if (state.value != UserState.Win) Const.targetEnd else progressAnimation
    }

    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        progress = progressAnimation
    )
}