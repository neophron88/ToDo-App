package org.rasulov.todoapp.data.sources.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.rasulov.todoapp.data.repository.entity.FindBy
import org.rasulov.todoapp.data.sources.database.room.ToDoDao
import org.rasulov.todoapp.data.sources.database.room.entities.ToDoDBEntity
import org.rasulov.todoapp.data.sources.database.room.entities.ToDoIDTuple
import org.rasulov.todoapp.domain.entities.ToDo

class RoomToDoSource(
    private val toDoDao: ToDoDao
) : DataBaseToDoSource {

    override fun getAllToDos(findBy: FindBy): Flowable<List<ToDo>> {
        val searchBy = findBy.searchBy
        val settings = findBy.appSettings
        return toDoDao
            .getAllTasks(searchBy.title, settings.priority.ordinal)
            .map { it.map(ToDoDBEntity::toToDo) }
    }

    override fun insertToDo(task: ToDo): Completable =
        toDoDao.insertTask(ToDoDBEntity.fromToDo(task))
            .subscribeOn(Schedulers.io())


    override fun updateToDo(task: ToDo): Completable =
        toDoDao.updateTask(ToDoDBEntity.fromToDo(task))
            .subscribeOn(Schedulers.io())


    override fun deleteToDo(toDoId: Long): Completable =
        toDoDao.deleteTask(ToDoIDTuple(toDoId))
            .subscribeOn(Schedulers.io())


    override fun deleteAllToDos(): Completable =
        toDoDao.deleteAll()
            .subscribeOn(Schedulers.io())

}