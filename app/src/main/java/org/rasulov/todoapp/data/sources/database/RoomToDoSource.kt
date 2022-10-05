package org.rasulov.todoapp.data.sources.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.rasulov.todoapp.data.repository.entity.FindBy
import org.rasulov.todoapp.data.sources.database.room.ToDoDao
import org.rasulov.todoapp.data.sources.database.room.entities.ToDoDBEntity
import org.rasulov.todoapp.data.sources.database.room.entities.ToDoIDTuple
import org.rasulov.todoapp.domain.entities.ToDo

class RoomToDoSource(
    private val toDoDao: ToDoDao
) : DataBaseToDoSource {

    override fun getAllToDos(findBy: FindBy): Flow<List<ToDo>> {
        val searchBy = findBy.searchBy
        val settings = findBy.appSettings
        return toDoDao
            .getAllTasks(searchBy.title, settings.priority.ordinal)
            .map { it.map(ToDoDBEntity::toToDo) }
    }

    override suspend fun insertToDo(task: ToDo) {
        toDoDao.insertTask(ToDoDBEntity.fromToDo(task))
    }

    override suspend fun updateToDo(task: ToDo) {
        toDoDao.updateTask(ToDoDBEntity.fromToDo(task))
    }

    override suspend fun deleteToDo(toDoId: Long) {
        toDoDao.deleteTask(ToDoIDTuple(toDoId))
    }

    override suspend fun deleteAllToDos() {
        toDoDao.deleteAll()
    }
}