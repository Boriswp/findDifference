package com.immo.FindTheDifferences.remote

import com.immo.FindTheDifferences.modules.PreferenceManager
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiInterface,
    private val prefMgr: PreferenceManager
) {
}