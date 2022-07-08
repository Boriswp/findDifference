package com.immo.findTheDifferences.prefs

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

val Context.themePrefDataStore by preferencesDataStore(PreferenceManagerImpl.PREFS_NAME)

class PreferenceManagerImpl @Inject constructor(@ApplicationContext context: Context) {


    private val dataStore = context.themePrefDataStore

    companion object {
        const val PREFS_NAME = "com.immo.FindTheDifferences"
    }
}

