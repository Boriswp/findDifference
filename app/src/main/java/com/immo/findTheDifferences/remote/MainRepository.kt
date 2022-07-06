package com.immo.findTheDifferences.remote

import com.immo.findTheDifferences.prefs.PreferenceManager
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiInterface,
    //private val prefMgr: PreferenceManager
) {
}