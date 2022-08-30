package org.rasulov.todoapp.sources.room.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.rasulov.todoapp.app.data.ToDoSource
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.sources.room.api.entities.ToDoDBEntity

class RoomToDoSource(private val toDoDao: ToDoDao) : ToDoSource {

    override fun getAllTasks(orderBy: Priority): Flow<List<ToDo>> {
        return toDoDao.getAllTasks(orderBy.ordinal)
            .map {
                it.map { todoDb -> todoDb.toToDo() }
            }
    }

    override suspend fun insertTask(task: ToDo) {
        toDoDao.insertTask(
            ToDoDBEntity(
                title = task.title,
                priority = task.priority.ordinal,
                description = task.description
            )
        )
    }
}