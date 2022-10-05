@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package org.rasulov.todoapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import org.rasulov.todoapp.data.repository.entity.FindBy
import org.rasulov.todoapp.data.sources.database.DataBaseToDoSource
import org.rasulov.todoapp.data.sources.preference.PreferenceToDoSource
import org.rasulov.todoapp.domain.Repository
import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.domain.entities.ToDoSearchBy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoRepository @Inject constructor(
    private val database: DataBaseToDoSource,
    private val preference: PreferenceToDoSource
) : Repository {

    private val searchBy = MutableLiveData(ToDoSearchBy())

    private val findByFlow = combine(
        searchBy.asFlow(),
        preference.getAppSettings(),
        ::find
    )

    override fun getAllToDos(): Flow<List<ToDo>> {
        return findByFlow.flatMapLatest {
            database.getAllToDos(it)
        }
    }

    override suspend fun addToDo(toDo: ToDo) {
        toDo.validate()
        database.insertToDo(toDo)
    }

    override suspend fun updateToDo(toDo: ToDo) {
        toDo.validate()
        database.updateToDo(toDo)
    }

    override suspend fun deleteToDo(toDoId: Long) {
        database.deleteToDo(toDoId)
    }

    override suspend fun deleteAllToDos() {
        database.deleteAllToDos()
    }

    override fun setSearchBy(searchBy: ToDoSearchBy) {
        this.searchBy.value = searchBy
    }

    override suspend fun setSettings(appSettings: AppSettings) {
        preference.setAppSettings(appSettings)
    }

    override fun getSettings(): Flow<AppSettings> {
        return preference.getAppSettings()
    }

    private fun find(
        searchBy: ToDoSearchBy,
        appSettings: AppSettings
    ) = FindBy(searchBy, appSettings)

}