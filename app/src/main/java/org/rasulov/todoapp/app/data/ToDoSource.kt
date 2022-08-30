package org.rasulov.todoapp.app.data

import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo

interface ToDoSource {

    fun getAllTasks(orderBy: Priority): Flow<List<ToDo>>

    suspend fun insertTask(task: ToDo)
}