package org.rasulov.todoapp.presentation.fragments.update

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.rasulov.utilities.lifecycle.observer.MutableSingleUseData
import org.rasulov.utilities.lifecycle.observer.toSingleUseData
import org.rasulov.todoapp.domain.EmptyFieldException
import org.rasulov.todoapp.domain.Repository
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.presentation.utils.EmptyField
import org.rasulov.todoapp.presentation.utils.OperationSuccess
import org.rasulov.todoapp.presentation.utils.UIEvent
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiEvent = MutableSingleUseData<UIEvent>()
    val uiEvent = _uiEvent.toSingleUseData()


    fun updateToDO(toDo: ToDo) = viewModelScope.launch {
        try {
            repository.updateToDo(toDo)
            _uiEvent.value = OperationSuccess
        } catch (e: EmptyFieldException) {
            _uiEvent.value = EmptyField
        }
    }

    fun deleteToDO(toDoId: Long) = viewModelScope.launch {
        repository.deleteToDo(toDoId)
        _uiEvent.value = OperationSuccess
    }
}