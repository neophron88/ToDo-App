package org.rasulov.todoapp.app.data

import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.app.domain.entities.Settings
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy

interface ToDoSource {

    fun getAllToDos(): Flow<List<ToDo>>

    suspend fun insertToDo(task: ToDo)

    fun searchToDosBy(searchBy: ToDoSearchBy)

    suspend fun setSettings(settings: Settings)

    fun getSettings(): Flow<Settings>
}