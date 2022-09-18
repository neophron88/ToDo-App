package org.rasulov.todoapp.app.presentation.fragments.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import org.rasulov.todoapp.app.domain.Repository
import org.rasulov.todoapp.app.domain.entities.Settings
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    val allToDos = repository.getAllToDos()


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