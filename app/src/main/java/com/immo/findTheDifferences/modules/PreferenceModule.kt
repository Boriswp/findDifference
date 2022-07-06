package com.immo.findTheDifferences.modules

import android.content.Context
import com.immo.findTheDifferences.prefs.PreferenceManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManagerImpl {
        return PreferenceManagerImpl(context = context)
    }
}
