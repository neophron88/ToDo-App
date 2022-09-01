package org.rasulov.todoapp.app.presentation.fragments.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.rasulov.todoapp.app.data.ToDoRepository
import org.rasulov.todoapp.app.domain.entities.ToDo

class AddViewModel(
    private val repository: ToDoRepository
) : ViewModel() {


    fun addToDo(toDo: ToDo) {
        viewModelScope.launch {
            repository.addToDo(toDo)
        }
    }
}