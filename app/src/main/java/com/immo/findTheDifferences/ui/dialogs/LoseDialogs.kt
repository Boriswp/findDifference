package com.immo.findTheDifferences.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import com.immo.findTheDifferences.R
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun YouLoseDialog(isGameScreen: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            isGameScreen.value = false
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "home"
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_retry),
                    contentDescription = "retry"
                )
            }
        },
        buttons = {

        }, shape = RoundedCornerShape(15.dp)
    )
}
