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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.immo.findTheDifferences.MainActivityViewModel

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
            Column(Modifier.padding(30.dp),
            horizontalAlignment = Alignment.End) {
                Text(
                    text = "Privacy Policy \n Boris built the The Mindfulness Test app as an Ad Supported app . This SERVICE is provided by Boris at no cost and is intended for use as is.This page is used to inform visitors regarding my policies with the collection, use, and disclosure of Personal Informationif anyone decided to use my Service.\n If you choose to use my Service, then you agree to the collection and use of information in relation tothis policy . The Personal Information that I collect is usedfor providing and improving the Service.I will not use or share your information with anyone except as described in this Privacy Policy.The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which are accessible at The Mindfulness Test unless otherwise defined inthis Privacy Policy.Information Collection and Use\n For a better experience,while using our Service, I may require you to provide us with certain personally identifiable information . The information that I request will be retained on your device and is not collected by me in any way.The app does use third - party services that may collect information used to identify you .\n Link to the privacy policy of third -party service providers used by the app\n Google Play Services\n AdMob\n Google Analytics\nfor Firebase Firebase Crashlytics Log Data\n I want to inform you that whenever you use my Service, in a case of an error in the app I collect data and information (through third -party products) on your phone called Log Data.This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing my Service, the time and date of your use of the Service, and other statistics.",
                    modifier = Modifier.verticalScroll(rememberScrollState()).weight(0.5f)
                )
                Button(onClick = { openDialog.value = false }, modifier = Modifier.weight(0.075F).padding(top=15.dp)) {
                    Text("OK")
                }
            }

        }
    }
}