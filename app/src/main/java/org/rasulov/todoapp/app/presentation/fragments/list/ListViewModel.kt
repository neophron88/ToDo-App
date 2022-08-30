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

class ListViewModel(
    private val repository: ToDoRepository
) : ViewModel() {

    private val searchBy = MutableLiveData(Priority.HIGH)

    val allToDos: Flow<List<ToDo>> = searchBy
        .asFlow()
        .flatMapLatest {
            repository.getAllToDos(it)
        }


    fun setSearchBy(priority: Priority) {
        searchBy.value = priority
    }

    fun addToDo(toDo: ToDo) {
        viewModelScope.launch {
            repository.addToDo(toDo)
        }
    }

}