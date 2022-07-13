package com.immo.findTheDifferences.remote

import com.immo.findTheDifferences.prefs.PreferenceManagerImpl
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiInterface,
    private val prefMgr: PreferenceManagerImpl
) {
    suspend fun getTxtFile() = apiService.downloadTxtFile()
}