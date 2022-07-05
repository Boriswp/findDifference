package ru.immo.datingapp.core.di

import android.content.Context
import android.preference.PreferenceManager
import com.immo.FindTheDifferences.modules.PreferenceManagerImpl
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
