package org.rasulov.todoapp.presentation.fragments.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.rasulov.todoapp.domain.Repository
import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.domain.entities.ToDoSearchBy
import org.rasulov.todoapp.presentation.utils.CanBeRestored
import org.rasulov.todoapp.presentation.utils.UIEvent
import org.rasulov.utilities.lifecycle.observer.MutableSingleUseData
import org.rasulov.utilities.lifecycle.observer.toSingleUseData
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiEvent = MutableSingleUseData<UIEvent>()
    val uiEvent = _uiEvent.toSingleUseData()

    val allToDos = repository.getAllToDos()


    fun deleteToDo(todo: ToDo) = viewModelScope.launch {
        repository.deleteToDo(todo.id)
        _uiEvent.value = CanBeRestored(todo)
    }


    fun addToDO(todo: ToDo) = viewModelScope.launch {
        repository.addToDo(todo)
    }


    fun setSearchBy(searchBy: ToDoSearchBy) {
        repository.setSearchBy(searchBy)
    }

    fun setSettings(appSettings: AppSettings) = viewModelScope.launch {
        repository.setSettings(appSettings)
    }

    fun deleteAllToDos() = viewModelScope.launch {
        repository.deleteAllToDos()
    }

}
