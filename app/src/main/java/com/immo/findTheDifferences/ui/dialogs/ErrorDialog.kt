package com.immo.findTheDifferences.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.ui.theme.Typography

@Composable
fun ErrorDialog(callback: () -> Unit) {
    Dialog(
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
        onDismissRequest = {
            callback()
        }) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = "home",
                    modifier = Modifier.clickable {
                        callback()
                    })

                Text(text = "ERROR.", style = Typography.h1)

                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "home",
                    modifier = Modifier.clickable {
                        callback()
                    })
            }
        }
    }
}