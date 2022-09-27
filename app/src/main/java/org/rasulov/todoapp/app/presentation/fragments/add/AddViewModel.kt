package org.rasulov.todoapp.app.presentation.fragments.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.rasulov.utilities.lifecycle.MutableSingleUseData
import org.rasulov.utilities.lifecycle.toSingleUseData
import org.rasulov.todoapp.app.data.EmptyFieldException
import org.rasulov.todoapp.app.domain.Repository
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.presentation.utils.EmptyFieldUIEvent
import org.rasulov.todoapp.app.presentation.utils.OperationSuccessUIEvent
import org.rasulov.todoapp.app.presentation.utils.UIEvent
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiEvent = MutableSingleUseData<UIEvent>()
    val uiEvent = _uiEvent.toSingleUseData()

    fun addToDo(toDo: ToDo) = viewModelScope.launch {
        try {
            repository.addToDo(toDo)
            _uiEvent.value = OperationSuccessUIEvent
        } catch (e: EmptyFieldException) {
            _uiEvent.value = EmptyFieldUIEvent
        }

    }
}