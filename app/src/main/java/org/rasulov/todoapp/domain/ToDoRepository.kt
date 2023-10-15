package org.rasulov.todoapp.domain

import io.reactivex.Completable
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.domain.entities.ToDoSearchBy

interface ToDoRepository {

    fun getAllToDos(): Flowable<List<ToDo>>

    fun addToDo(toDo: ToDo): Completable

    fun updateToDo(toDo: ToDo): Completable

    fun deleteToDo(toDoId: Long): Completable

    fun setSearchBy(searchBy: ToDoSearchBy)

    fun setSettings(appSettings: AppSettings): Completable

    fun getSettings(): Flowable<AppSettings>

    fun deleteAllToDos(): Completable
}