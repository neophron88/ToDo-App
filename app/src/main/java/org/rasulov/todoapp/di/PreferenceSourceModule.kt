package org.rasulov.todoapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.rasulov.todoapp.data.sources.preference.PreferenceToDoSource
import org.rasulov.todoapp.data.sources.preference.PreferenceToDoSourceImpl
import org.rasulov.todoapp.data.sources.preference.preferenceApi.Preference
import org.rasulov.todoapp.data.sources.preference.preferenceApi.PreferenceBuilder
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PreferenceSourceModule {


    @Singleton
    @Provides
    fun providePreferenceSource(preference: Preference): PreferenceToDoSource {
        return PreferenceToDoSourceImpl(preference)
    }


    @Singleton
    @Provides
    fun providePreference(@ApplicationContext context: Context): Preference {
        return PreferenceBuilder(context).build("appSettings")
    }
}