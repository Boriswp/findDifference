package com.immo.findTheDifferences.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.localization.Vocabulary
import com.immo.findTheDifferences.localization.*
import com.immo.findTheDifferences.ui.theme.Typography

@Composable
fun GreetingScreen(isGameScreen: MutableState<Boolean>) {
    val localization = Vocabulary.localization
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Image(painter = painterResource(id = R.drawable.ic_info),
                contentDescription = "info")
        }
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(20.dp), contentAlignment = Alignment.TopEnd
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_settings),
//                contentDescription = "settings"
//            )
//        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = localization.main_title(), style = Typography.h1)
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            TextButton(onClick = { isGameScreen.value = true }) {
                Text(text = localization.main_play_button(), style = Typography.h1)
            }
        }
    }
}