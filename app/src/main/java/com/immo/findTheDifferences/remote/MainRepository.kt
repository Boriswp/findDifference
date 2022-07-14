package com.immo.findTheDifferences.remote

import com.immo.findTheDifferences.prefs.PreferenceManagerImpl
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiInterface,
    private val prefMgr: PreferenceManagerImpl
) {
    suspend fun getTxtFile() = apiService.downloadTxtFile()
    suspend fun getIds() = prefMgr.getLvlOrder()
    suspend fun setIds(ids: List<Int>) = prefMgr.setLvlOrder(ids)
    suspend fun setCurrentLvl(lvlId: Int) = prefMgr.setCurrentLvl(lvlId)
    suspend fun getCurrentLvl() = prefMgr.getCurrentLvl()
    suspend fun setCurrentDate(dateTime: String) = prefMgr.setCurrentDate(dateTime)
    suspend fun getCurrentDate() = prefMgr.getCurrentDate()
}