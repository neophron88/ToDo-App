package org.rasulov.todoapp.app.data

import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.app.domain.Repository
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.Settings
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy

class ToDoRepository(
    private val source: ToDoSource
) : Repository {

    fun getAllToDos(): Flow<List<ToDo>> {
        return source.getAllToDos()
    }

    suspend fun addToDo(toDo: ToDo) {
        toDo.validate()
        source.insertToDo(toDo)
    }

    suspend fun updateToDo(toDo: ToDo) {
        toDo.validate()
        source.updateToDo(toDo)
    }

    suspend fun deleteToDo(toDoId: Long) {
        source.deleteToDo(toDoId)
    }

    fun setSearchBy(searchBy: ToDoSearchBy) {
        source.searchToDosBy(searchBy)
    }

    suspend fun setSettings(settings: Settings) {
        source.setSettings(settings)
    }

    fun getSettings(): Flow<Settings> {
        return source.getSettings()
    }

    suspend fun deleteAllToDos() {
        source.deleteAllToDos()
    }


}