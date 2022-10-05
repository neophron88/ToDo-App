package org.rasulov.todoapp.data.sources.database

import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.data.repository.entity.FindBy
import org.rasulov.todoapp.domain.entities.ToDo

interface DataBaseToDoSource {

    fun getAllToDos(findBy: FindBy): Flow<List<ToDo>>

    suspend fun insertToDo(task: ToDo)

    suspend fun updateToDo(task: ToDo)

    suspend fun deleteToDo(toDoId: Long)

    suspend fun deleteAllToDos()

}