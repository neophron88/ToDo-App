package org.rasulov.todoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.rasulov.todoapp.app.data.todo.ToDoSource
import org.rasulov.todoapp.sources.persistence_source.PersistenceToDoSource

@Module
@InstallIn(SingletonComponent::class)
interface SourceModule {

    @Binds
    fun bindToDoSource(impl: PersistenceToDoSource): ToDoSource
}