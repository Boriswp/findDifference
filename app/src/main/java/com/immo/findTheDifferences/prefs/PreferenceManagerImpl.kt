package com.immo.findTheDifferences.prefs

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.themePrefDataStore by preferencesDataStore(PreferenceManagerImpl.PREFS_NAME)
val Context.dataStore by dataStore("user_ids.json", serializer = UserIdsPrefsSerializer)

@Singleton
class PreferenceManagerImpl @Inject constructor(@ApplicationContext context: Context) {


    private val dataStore = context.themePrefDataStore
    private val userData = context.dataStore

    suspend fun setLvlOrder(idArray: List<Int>) {
        userData.updateData {
            UserIdsPrefs(
                ids = idArray
            )
        }
    }

    suspend fun getLvlOrder(): UserIdsPrefs {
        return userData.data.catch {
            if (it is IOException) {
                it.printStackTrace()
            } else {
                throw it
            }
        }.map { it }.first()
    }

    suspend fun setCurrentLvl(currLvl: Int) {
        dataStore.edit {
            it[PREFS_CURRENT_LVL] = currLvl
        }
    }

    suspend fun getCurrentLvl(): Int {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            preference[PREFS_CURRENT_LVL] ?: 0
        }.first()
    }

    suspend fun isFirstLaunch(): Boolean {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            preference[PREFS_FIRST_LAUNCH] ?: true
        }.first()
    }

    suspend fun setIsFirstLaunch() {
        dataStore.edit {
            it[PREFS_FIRST_LAUNCH] = false
        }
    }

    suspend fun setCurrentDate(dateTime: String) {
        dataStore.edit {
            it[PREFS_DATE_TIME] = dateTime
        }
    }

    suspend fun getCurrentDate(): String {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            preference[PREFS_DATE_TIME] ?: ""
        }.first()
    }


    companion object {
        const val PREFS_NAME = "com.immo.FindTheDifferences"
        val PREFS_CURRENT_LVL = intPreferencesKey("lvl_num")
        val PREFS_DATE_TIME = stringPreferencesKey("date_time")
        val PREFS_FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    }
}

