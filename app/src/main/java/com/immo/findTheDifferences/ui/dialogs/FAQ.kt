package com.immo.findTheDifferences.ui.dialogs

import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.immo.findTheDifferences.MainActivityViewModel
import com.immo.findTheDifferences.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FAQ(openDialog: MutableState<Boolean>) {
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
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current
                AndroidView(factory = {
                    WebView(context).apply {
                        val cookieManager = CookieManager.getInstance()
                        cookieManager.removeAllCookies { loadUrl("https://borisgames.ru/policy_c4ca4238a0b923820dcc509a6f75849b.html") }
                        settings.javaScriptEnabled = false
                        settings.databaseEnabled = false
                        settings.domStorageEnabled = false
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                }, modifier = Modifier.fillMaxWidth()
                    .weight(7f))
                Button(
                    onClick = { openDialog.value = false }, modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Text("OK")
                }
            }

        }
    }
}