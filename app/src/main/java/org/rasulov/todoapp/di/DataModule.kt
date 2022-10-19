package org.rasulov.todoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.rasulov.todoapp.data.repository.ToDoRepositoryImpl
import org.rasulov.todoapp.domain.ToDoRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindToDoRepository(impl:ToDoRepositoryImpl):ToDoRepository

}