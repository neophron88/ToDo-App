package org.rasulov.todoapp.app.domain

import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.app.domain.entities.Settings
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy

interface Repository {

    fun getAllToDos(): Flow<List<ToDo>>

    suspend fun addToDo(toDo: ToDo)

    suspend fun updateToDo(toDo: ToDo)

    suspend fun deleteToDo(toDoId: Long)

    fun setSearchBy(searchBy: ToDoSearchBy)

    suspend fun setSettings(settings: Settings)

    fun getSettings(): Flow<Settings>

    suspend fun deleteAllToDos()
}