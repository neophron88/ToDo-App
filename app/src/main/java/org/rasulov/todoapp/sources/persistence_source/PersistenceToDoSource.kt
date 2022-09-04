package org.rasulov.todoapp.sources.persistence_source

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.rasulov.todoapp.app.data.todo.ToDoSource
import org.rasulov.todoapp.app.domain.entities.Settings
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy
import org.rasulov.todoapp.sources.persistence_source.preference.ToDoPrefAccessor
import org.rasulov.todoapp.sources.persistence_source.room.ToDoDao
import org.rasulov.todoapp.sources.persistence_source.room.entities.ToDoDBEntity
import org.rasulov.todoapp.sources.persistence_source.room.entities.ToDoIDTuple

class PersistenceToDoSource(
    private val toDoDao: ToDoDao,
    private val toDoPrefAccessor: ToDoPrefAccessor
) : ToDoSource {

    private val searchBy = MutableLiveData(ToDoSearchBy())

    private val findByFlow = combine(
        searchBy.asFlow(),
        toDoPrefAccessor.getSettings(),
        ::find
    )


    override fun getAllToDos(): Flow<List<ToDo>> {
        return findByFlow
            .flatMapLatest { findBy ->
                getAllToDos(findBy)
            }
    }

    private fun getAllToDos(findBy: FindBy): Flow<List<ToDo>> {
        val searchBy = findBy.searchBy
        val settings = findBy.settings

        return toDoDao.getAllTasks(searchBy.title, settings.priority.ordinal)
            .map {
                it.map { todoDb -> todoDb.toToDo() }
            }
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

    override fun searchToDosBy(searchBy: ToDoSearchBy) {
        this.searchBy.value = searchBy
    }

    override suspend fun setSettings(settings: Settings) {
        toDoPrefAccessor.setSettings(settings)
    }

    override fun getSettings(): Flow<Settings> {
        return toDoPrefAccessor.getSettings()
    }

    private fun find(
        searchBy: ToDoSearchBy,
        settings: Settings
    ) = FindBy(searchBy, settings)


}