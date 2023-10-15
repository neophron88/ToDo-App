package org.rasulov.todoapp.data.sources.database

import io.reactivex.Completable
import io.reactivex.Flowable
import org.rasulov.todoapp.data.repository.entity.FindBy
import org.rasulov.todoapp.domain.entities.ToDo

interface DataBaseToDoSource {

    fun getAllToDos(findBy: FindBy): Flowable<List<ToDo>>

    fun insertToDo(task: ToDo): Completable

    fun updateToDo(task: ToDo): Completable

    fun deleteToDo(toDoId: Long): Completable

    fun deleteAllToDos(): Completable

}