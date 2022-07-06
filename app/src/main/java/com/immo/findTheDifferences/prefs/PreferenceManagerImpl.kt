package com.immo.findTheDifferences.prefs

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Singleton

val Context.themePrefDataStore by preferencesDataStore(PreferenceManager.PREFS_NAME)

class PreferenceManagerImpl(context: Context) : PreferenceManager {


    private val dataStore = context.themePrefDataStore


    companion object {
    }
}


@Singleton
interface PreferenceManager {

    companion object {
        const val PREFS_NAME = "com.immo.FindTheDifferences"
    }

}
