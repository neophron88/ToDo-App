package org.rasulov.todoapp.app.data.todo

import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.app.domain.Repository
import org.rasulov.todoapp.app.domain.entities.Settings
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoRepository @Inject constructor(
    private val source: ToDoSource
) : Repository {

    override fun getAllToDos(): Flow<List<ToDo>> {
        return source.getAllToDos()
    }

    override suspend fun addToDo(toDo: ToDo) {
        toDo.validate()
        source.insertToDo(toDo)
    }

    override suspend fun updateToDo(toDo: ToDo) {
        toDo.validate()
        source.updateToDo(toDo)
    }

    override suspend fun deleteToDo(toDoId: Long) {
        source.deleteToDo(toDoId)
    }

    override fun setSearchBy(searchBy: ToDoSearchBy) {
        source.searchToDosBy(searchBy)
    }

    override suspend fun setSettings(settings: Settings) {
        source.setSettings(settings)
    }

    override fun getSettings(): Flow<Settings> {
        return source.getSettings()
    }

    override suspend fun deleteAllToDos() {
        source.deleteAllToDos()
    }
}