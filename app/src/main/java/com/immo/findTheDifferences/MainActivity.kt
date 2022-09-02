package com.immo.findTheDifferences

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.immo.findTheDifferences.localization.setSupportedLocalesNow
import com.immo.findTheDifferences.ui.screens.MainScreen
import com.immo.findTheDifferences.ui.theme.MyApplicationTheme
import com.yandex.metrica.YandexMetrica
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportedLocalesNow
        MobileAds.initialize(this) {}
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val screen = LocalConfiguration.current
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = (screen.screenHeightDp / 14).dp)
                        ) {
                            MainScreen(viewModel)
                        }
                        AndroidView(
                            modifier = Modifier.fillMaxWidth(),
                            factory = { context ->
                                AdView(context).apply {
                                    setAdSize(AdSize.BANNER)
                                    adUnitId = Const.AD_BANNER_ID
                                    loadAd(AdRequest.Builder().build())
                                }
                            }
                        )
                    }

                }
            }
        }
        observeViewModel()
    }

    override fun onStart() {
        YandexMetrica.reportEvent("StartGame")
        super.onStart()
    }


    override fun onPause() {
        lifecycleScope.launch {
            val tuple = viewModel.getCurrLvl()
            Log.d("currLvl",tuple.first.toString())
            val eventParameters = "{\"lvl\":\"${tuple.first}\",\"id\":\"${tuple.second}\"}"
            YandexMetrica.reportEvent("GameOnPause", eventParameters)
        }
        super.onPause()
    }


    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is InternetState.Fetched -> {

                        }
                        is InternetState.Error -> {
                            viewModel.showError()
                        }
                    }
                }
            }
        }
    }
}
