package org.rasulov.todoapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.rasulov.todoapp.sources.persistence_source.room.ToDoDao
import org.rasulov.todoapp.sources.persistence_source.room.ToDoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {


    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ToDoDatabase {
        return ToDoDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideToDoDao(database: ToDoDatabase): ToDoDao {
        return database.getToDoDao()
    }

}