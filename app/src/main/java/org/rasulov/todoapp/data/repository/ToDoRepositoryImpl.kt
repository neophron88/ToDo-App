@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package org.rasulov.todoapp.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.rasulov.todoapp.data.repository.entity.FindBy
import org.rasulov.todoapp.data.sources.database.DataBaseToDoSource
import org.rasulov.todoapp.data.sources.preference.PreferenceToDoSource
import org.rasulov.todoapp.domain.ToDoRepository
import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.domain.entities.ToDoSearchBy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoRepositoryImpl @Inject constructor(
    private val database: DataBaseToDoSource,
    private val preference: PreferenceToDoSource
) : ToDoRepository {

    private val searchBy = BehaviorProcessor.createDefault(ToDoSearchBy())

    private val findByFlow = Flowable.combineLatest(
        searchBy, preference.getAppSettings()
    ) { searchBy, appSettings -> FindBy(searchBy, appSettings) }

    override fun getAllToDos(): Flowable<List<ToDo>> =
        findByFlow.switchMap {
            database.getAllToDos(it)
        }

    override fun addToDo(toDo: ToDo): Completable =
        Completable
            .fromAction { toDo.validate() }
            .andThen(database.insertToDo(toDo))

    override fun updateToDo(toDo: ToDo): Completable =
        Completable
            .fromAction { toDo.validate() }
            .andThen(database.updateToDo(toDo))

    override fun deleteToDo(toDoId: Long): Completable =
        database.deleteToDo(toDoId)


    override fun deleteAllToDos(): Completable =
        database.deleteAllToDos()


    override fun setSearchBy(searchBy: ToDoSearchBy) {
        this.searchBy.onNext(searchBy)
    }

    override fun setSettings(appSettings: AppSettings): Completable =
        preference.setAppSettings(appSettings)


    override fun getSettings(): Flowable<AppSettings> =
        preference.getAppSettings()


}