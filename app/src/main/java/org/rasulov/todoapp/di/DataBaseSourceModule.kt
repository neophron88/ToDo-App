package org.rasulov.todoapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.rasulov.todoapp.data.sources.database.DataBaseToDoSource
import org.rasulov.todoapp.data.sources.database.RoomToDoSource
import org.rasulov.todoapp.data.sources.database.room.ToDoDao
import org.rasulov.todoapp.data.sources.database.room.ToDoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseSourceModule {


    @Singleton
    @Provides
    fun provideDataBaseSource(toDoDao: ToDoDao): DataBaseToDoSource {
        return RoomToDoSource(toDoDao)
    }

    @Singleton
    @Provides
    fun provideToDoDao(database: ToDoDatabase): ToDoDao {
        return database.getToDoDao()
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ToDoDatabase {
        return ToDoDatabase.getDatabase(context)
    }

}