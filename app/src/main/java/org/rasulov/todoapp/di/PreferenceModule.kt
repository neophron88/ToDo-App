package org.rasulov.todoapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.rasulov.todoapp.sources.persistence_source.preference.ToDoPrefAccessor
import org.rasulov.todoapp.sources.persistence_source.preference.ToDoPreference
import org.rasulov.todoapp.sources.persistence_source.room.ToDoDao
import org.rasulov.todoapp.sources.persistence_source.room.ToDoDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {


    @Singleton
    @Provides
    fun providePreference(@ApplicationContext context: Context): ToDoPreference {
        return ToDoPreference.getPreference(context)
    }

    @Singleton
    @Provides
    fun provideToDoPrefAccessor(toDoPreference: ToDoPreference): ToDoPrefAccessor {
        return toDoPreference.getToDoPrefAccessor()
    }
}