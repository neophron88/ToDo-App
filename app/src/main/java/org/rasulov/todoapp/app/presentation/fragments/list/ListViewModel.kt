package org.rasulov.todoapp.app.presentation.fragments.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import org.rasulov.todoapp.app.data.ToDoRepository
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.Settings
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy

class ListViewModel(
    private val repository: ToDoRepository
) : ViewModel() {


    fun getAllToDos(): Flow<List<ToDo>> {
        return repository.getAllToDos()
    }

    fun deleteToDo(id: Long) {
        viewModelScope.launch {
            repository.deleteToDo(id)
        }
    }

    fun addToDO(todo: ToDo) {
        viewModelScope.launch {
            repository.addToDo(todo)
        }
    }


    fun setSearchBy(searchBy: ToDoSearchBy) {
        repository.setSearchBy(searchBy)
    }

    fun setSettings(settings: Settings) {
        viewModelScope.launch {
            repository.setSettings(settings)
        }
    }

    fun deleteAllToDos() {
        viewModelScope.launch {
            repository.deleteAllToDos()
        }
    }

}