package org.rasulov.todoapp.presentation.fragments.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.rasulov.utilities.lifecycle.observer.MutableSingleUseData
import org.rasulov.utilities.lifecycle.observer.toSingleUseData
import org.rasulov.todoapp.domain.EmptyFieldException
import org.rasulov.todoapp.domain.ToDoRepository
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.presentation.utils.EmptyField
import org.rasulov.todoapp.presentation.utils.OperationSuccess
import org.rasulov.todoapp.presentation.utils.UIEvent
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private val _uiEvent = MutableSingleUseData<UIEvent>()
    val uiEvent = _uiEvent.toSingleUseData()

    fun addToDo(toDo: ToDo) = viewModelScope.launch {
        try {
            toDoRepository.addToDo(toDo)
            _uiEvent.value = OperationSuccess
        } catch (e: EmptyFieldException) {
            _uiEvent.value = EmptyField
        }

    }
}