package com.immo.findTheDifferences.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.immo.findTheDifferences.MainActivityViewModel
import com.immo.findTheDifferences.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FAQ(viewModel: MainActivityViewModel, openDialog: MutableState<Boolean>) {
    val configuration = LocalConfiguration.current
    Dialog(
        onDismissRequest = {
            openDialog.value = false
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.Inherit,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .padding(
                    top = configuration.screenHeightDp.dp / 10,
                    bottom = configuration.screenHeightDp.dp / 10,
                    start = 10.dp,
                    end = 10.dp
                )
                .fillMaxWidth(),
            elevation = 8.dp
        ) {
            Column(
                Modifier.padding(30.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.policy),
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(0.5f)
                )
                Button(
                    onClick = { openDialog.value = false }, modifier = Modifier
                        .weight(0.075F)
                        .padding(top = 15.dp)
                ) {
                    Text("OK")
                }
            }

        }
    }
}