package com.immo.findTheDifferences.ui.dialogs

import androidx.compose.foundation.Image
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
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.localization.*
import com.immo.findTheDifferences.localization.Vocabulary.localization
import com.immo.findTheDifferences.ui.theme.Typography

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
                    text = localization.dialog_win(),
                    textAlign = TextAlign.Center,
                    style = Typography.h1
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_home),
                        contentDescription = "home"
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "next"
                    )
                }
            }
        },
        buttons = {

        }, shape = RoundedCornerShape(15.dp)
    )
}
