package com.immo.findTheDifferences.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import com.immo.findTheDifferences.R
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.immo.findTheDifferences.ui.components.SimpleButton
import com.immo.findTheDifferences.ui.screens.showRewardedAD

@Composable
fun YouLoseDialog(isGameScreen: MutableState<Boolean>, callback: (hintReward: Boolean) -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = {
            isGameScreen.value = false
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                SimpleButton(id = R.drawable.ic_home) {
                    isGameScreen.value = false
                }

                SimpleButton(id = R.drawable.ic_retry) {
                    callback(false)
                }

                SimpleButton(id = R.drawable.ic_hint) {
                    showRewardedAD(
                        context = context,
                        callback = { callback(it) })
                }
            }
        },
        buttons = {

        }, shape = RoundedCornerShape(15.dp)
    )
}
