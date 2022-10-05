package org.rasulov.todoapp.domain

import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.domain.entities.ToDoSearchBy

interface Repository {

    fun getAllToDos(): Flow<List<ToDo>>

    suspend fun addToDo(toDo: ToDo)

    suspend fun updateToDo(toDo: ToDo)

    suspend fun deleteToDo(toDoId: Long)

    fun setSearchBy(searchBy: ToDoSearchBy)

    suspend fun setSettings(appSettings: AppSettings)

    fun getSettings(): Flow<AppSettings>

    suspend fun deleteAllToDos()
}