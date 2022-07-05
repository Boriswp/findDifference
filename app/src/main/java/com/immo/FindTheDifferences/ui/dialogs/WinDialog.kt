package com.immo.FindTheDifferences.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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
                    text = "Ты Выиграл!",
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        isGameScreen.value = false
                    }, modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(40.dp)
                ) {
                    Text("В меню")
                }

                Button(
                    onClick = {
                    }, modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(40.dp)
                ) {
                    Text("Далее")
                }
            }
        },
        buttons = {

        }, shape = RoundedCornerShape(15.dp)
    )
}
