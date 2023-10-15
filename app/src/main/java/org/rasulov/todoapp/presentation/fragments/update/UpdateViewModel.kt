package org.rasulov.todoapp.presentation.fragments.update

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import org.rasulov.todoapp.domain.ToDoRepository
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.presentation.utils.AutoCloseDisposable
import org.rasulov.todoapp.presentation.utils.EmptyField
import org.rasulov.todoapp.presentation.utils.OperationSuccess
import org.rasulov.todoapp.presentation.utils.UIEvent
import org.rasulov.todoapp.utilities.lifecycle.observer.MutableSingleUseData
import org.rasulov.todoapp.utilities.lifecycle.observer.toSingleUseData
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private val disposables = AutoCloseDisposable()

    private val _uiEvent = MutableSingleUseData<UIEvent>()
    val uiEvent = _uiEvent.toSingleUseData()


    fun updateToDO(toDo: ToDo) {
        disposables + toDoRepository
            .updateToDo(toDo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _uiEvent.value = OperationSuccess },
                { _uiEvent.value = EmptyField }
            )
    }

    fun deleteToDO(toDoId: Long) {
        disposables + toDoRepository
            .deleteToDo(toDoId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _uiEvent.value = OperationSuccess }
    }
}