package com.immo.findTheDifferences.ui.dialogs

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.localization.*
import com.immo.findTheDifferences.localization.Vocabulary.localization
import com.immo.findTheDifferences.ui.theme.Typography

@Composable
fun EndLvlsDialog(isGameScreen: MutableState<Boolean>) {
    val context = LocalContext.current
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
                    text = localization.game_end(),
                    textAlign = TextAlign.Center,
                    style = Typography.h1
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_home),
                        contentDescription = "home",
                        modifier = Modifier.clickable {
                            val manager = ReviewManagerFactory.create(context)
                            val request = manager.requestReviewFlow()
                            request.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val reviewInfo = task.result
                                    val flow =
                                        manager.launchReviewFlow(context as Activity, reviewInfo)
                                    flow.addOnCompleteListener { _ ->
                                        isGameScreen.value = false
                                    }
                                } else {
                                    val reviewErrorCode = task.exception
                                }
                            }
                        }
                    )
                }
            }
        },
        buttons = {

        }, shape = RoundedCornerShape(15.dp)
    )
}