package com.immo.findTheDifferences.ui.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.*
import coil.request.ImageRequest
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.immo.findTheDifferences.*
import com.immo.findTheDifferences.R
import com.immo.findTheDifferences.earthQuake.EarthquakeBox
import com.immo.findTheDifferences.localization.Vocabulary.localization
import com.immo.findTheDifferences.localization.game_lvl_finished
import com.immo.findTheDifferences.localization.game_lvl_of
import com.immo.findTheDifferences.remote.FilesData
import com.immo.findTheDifferences.ui.components.TimeIndicator
import com.immo.findTheDifferences.ui.dialogs.EndLvlsDialog
import com.immo.findTheDifferences.ui.dialogs.YouLoseDialog
import com.immo.findTheDifferences.ui.dialogs.YouWinDialog
import com.immo.findTheDifferences.ui.theme.Typography
import timber.log.Timber
import kotlin.random.Random

@Composable
fun GameScreen(isGameScreen: MutableState<Boolean>, viewModel: MainActivityViewModel) {

    val currState = remember {
        mutableStateOf<UserState>(UserState.Initial)
    }
    val tapCounts = rememberSaveable {
        mutableStateOf(0)
    }

    when (val res = viewModel.lvlDataViewState.collectAsState().value) {
        is LvlDataViewState.Success -> {
            val lvlList = res.response.filesData
            val listIndexes = res.response.indexes
            val currLvl = rememberSaveable {
                mutableStateOf(res.response.currLvl)
            }
            Column(modifier = Modifier.fillMaxSize()) {

                when (currState.value) {
                    is UserState.Initial -> {
                        LvlLogic(
                            currState = currState,
                            lvlList,
                            listIndexes,
                            lvlList.size,
                            currLvl,
                            tapCounts
                        )
                    }
                    is UserState.Lose -> {
                        YouLoseDialog(isGameScreen = isGameScreen) {
                            tapCounts.value = 0
                            currState.value = UserState.Initial
                        }
                    }
                    is UserState.Win -> {
                        if (currLvl.value < lvlList.size - 1) {
                            YouWinDialog(isGameScreen = isGameScreen) {
                                currLvl.value++
                                viewModel.setCurrLvl(currLvl.value)
                                tapCounts.value = 0
                                if (currLvl.value % 2 == 0) {
                                    if (viewModel.isFirstLaunch && currLvl.value < 2) {
                                        currState.value = UserState.Initial
                                    } else {
                                        currState.value = UserState.ShowAd
                                    }
                                } else {
                                    currState.value = UserState.Initial
                                }
                            }
                        } else {
                            EndLvlsDialog(isGameScreen = isGameScreen)
                        }
                    }
                    is UserState.ShowAd -> {
                        showAd(LocalContext.current) { currState.value = UserState.Initial }
                    }
                }
            }
        }
        is LvlDataViewState.Error -> {
            //TODO
        }
    }
}

