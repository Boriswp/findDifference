package com.immo.findTheDifferences.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.localization.*
import com.immo.findTheDifferences.localization.Vocabulary.localization
import com.immo.findTheDifferences.ui.components.SimpleButton
import com.immo.findTheDifferences.ui.theme.Typography

@Composable
fun YouWinDialog(isGameScreen: MutableState<Boolean>, callback: () -> Unit) {
    AlertDialog(
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
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
                    text = localization.dialog_win(),
                    textAlign = TextAlign.Center,
                    style = Typography.h2
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    SimpleButton(id = R.drawable.ic_home) {
                        isGameScreen.value = false
                    }

                    SimpleButton(id = R.drawable.ic_next) {
                        callback()
                    }
                }
            }
        },
        buttons = {

        }, shape = RoundedCornerShape(15.dp)
    )
}
