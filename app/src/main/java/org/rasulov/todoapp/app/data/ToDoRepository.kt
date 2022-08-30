package org.rasulov.todoapp.app.data

import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.app.domain.Repository
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo

class ToDoRepository(
    private val source: ToDoSource
) : Repository {

    fun getAllToDos(orderBy: Priority): Flow<List<ToDo>> {
        return source.getAllTasks(orderBy)
    }

    suspend fun addToDo(toDo: ToDo) {
        toDo.validate()
        source.insertTask(toDo)
    }


}