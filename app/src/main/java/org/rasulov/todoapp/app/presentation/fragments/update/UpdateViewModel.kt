package org.rasulov.todoapp.app.presentation.fragments.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.rasulov.todoapp.app.data.ToDoRepository
import org.rasulov.todoapp.app.domain.entities.ToDo

class UpdateViewModel(
    private val repository: ToDoRepository
) : ViewModel() {


    fun updateToDO(toDo: ToDo) {
        viewModelScope.launch {
            repository.updateToDo(toDo)
        }
    }

    fun deleteToDO(toDoId: Long) {
        viewModelScope.launch {
            repository.deleteToDo(toDoId)
        }
    }
}