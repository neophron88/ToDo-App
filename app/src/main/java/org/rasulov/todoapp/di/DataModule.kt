package org.rasulov.todoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.rasulov.todoapp.data.repository.ToDoRepository
import org.rasulov.todoapp.domain.Repository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindToDoRepository(impl:ToDoRepository):Repository

}