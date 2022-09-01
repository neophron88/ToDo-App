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
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy

class ListViewModel(
    private val repository: ToDoRepository
) : ViewModel() {


    fun getAllToDos(): Flow<List<ToDo>> {
        return repository.getAllToDos()
    }

    fun setSearchBy(searchBy: ToDoSearchBy) {
        repository.setSearchBy(searchBy)
    }

}