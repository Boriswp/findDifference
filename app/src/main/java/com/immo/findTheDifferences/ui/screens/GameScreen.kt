package com.immo.findTheDifferences.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.immo.findTheDifferences.LvlDataViewState
import com.immo.findTheDifferences.MainActivityViewModel
import com.immo.findTheDifferences.UserState
import com.immo.findTheDifferences.ui.dialogs.EndLvlsDialog
import com.immo.findTheDifferences.ui.dialogs.ErrorDialog
import com.immo.findTheDifferences.ui.dialogs.YouLoseDialog
import com.immo.findTheDifferences.ui.dialogs.YouWinDialog
import com.yandex.metrica.YandexMetrica


@Composable
fun GameScreen(isGameScreen: MutableState<Boolean>, viewModel: MainActivityViewModel) {

    val tapCounts = rememberSaveable {
        mutableStateOf(0)
    }
    val hint = remember {
        mutableStateOf(false)
    }

    when (val res = viewModel.lvlDataViewState.collectAsState().value) {
        is LvlDataViewState.Success -> {
            val lvlList = res.response.filesData
            val listIndexes = res.response.indexes
            val currLvl = rememberSaveable {
                mutableStateOf(res.response.currLvl)
            }
            val currState = remember {
                mutableStateOf(if (currLvl.value == lvlList.size) UserState.Win else UserState.Initial)
            }

            Column(modifier = Modifier.fillMaxSize()) {

                when (currState.value) {
                    is UserState.Initial -> {
                        val eventParameters = "{\"lvl\":\"${currLvl.value}\",\"id\":\"${lvlList[currLvl.value].level_id}\"}"
                        YandexMetrica.reportEvent("Curr Lvl", eventParameters)
                        LvlLogic(
                            currState = currState,
                            lvlList,
                            listIndexes,
                            lvlList.size,
                            currLvl,
                            tapCounts,
                            hint
                        ) {
                            currState.value = UserState.Win
                            currLvl.value++
                            viewModel.setCurrLvl(currLvl.value)
                            hint.value = false
                        }
                    }
                    is UserState.Lose -> {
                        val eventParameters = "{\"lvl\":\"${currLvl.value}\",\"id\":\"${lvlList[currLvl.value].level_id}\"}"
                        YandexMetrica.reportEvent("Lose", eventParameters)
                        YouLoseDialog(isGameScreen = isGameScreen) {
                            hint.value = it
                            tapCounts.value = 0
                            currState.value = UserState.Initial
                        }
                    }
                    is UserState.Win -> {
                        if (currLvl.value < lvlList.size) {
                            val eventParameters = "{\"lvl\":\"$currLvl\",\"id\":\"${lvlList[currLvl.value].level_id}\"}"
                            YandexMetrica.reportEvent("Win", eventParameters)
                            YouWinDialog(isGameScreen = isGameScreen) {
                                tapCounts.value = 0
                                //currLvl.value % 3 == 0
                                if (false) {
                                    if (currLvl.value == 0) {
                                        currState.value = UserState.Initial
                                    } else {
                                        currState.value = UserState.ShowAd
                                    }
                                } else {
                                    currState.value = UserState.Initial
                                }
                            }
                        } else {
                            YandexMetrica.reportEvent("EndGame")
                            EndLvlsDialog {
                                if (it) {
                                    viewModel.dropLvls()
                                } else {
                                    isGameScreen.value = false
                                }
                            }
                        }
                    }
                    is UserState.ShowAd -> {
                        showAd(LocalContext.current) { currState.value = UserState.Initial }
                    }
                }
            }
        }
        is LvlDataViewState.Error -> {
            ErrorDialog {
                isGameScreen.value = false
            }
        }
    }
}

