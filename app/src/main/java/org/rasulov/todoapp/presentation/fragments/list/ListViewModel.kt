package org.rasulov.todoapp.presentation.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import org.rasulov.todoapp.domain.ToDoRepository
import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.domain.entities.ToDoSearchBy
import org.rasulov.todoapp.presentation.utils.AutoCloseDisposable
import org.rasulov.todoapp.presentation.utils.CanBeRestored
import org.rasulov.todoapp.presentation.utils.UIEvent
import org.rasulov.todoapp.presentation.utils.UiState
import org.rasulov.todoapp.utilities.lifecycle.observer.MutableSingleUseData
import org.rasulov.todoapp.utilities.lifecycle.observer.toSingleUseData
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private val disposables = AutoCloseDisposable()

    private val _uiState = MutableLiveData(UiState<ToDo>(isLoading = true))
    val uiState: LiveData<UiState<ToDo>> get() = _uiState

    private val _uiEvent = MutableSingleUseData<UIEvent>()
    val uiEvent = _uiEvent.toSingleUseData()

    init {
        getAllToDos()
    }

    private fun getAllToDos() = disposables + toDoRepository
        .getAllToDos()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            _uiState.value = UiState(data = it)
        }


    fun deleteToDo(todo: ToDo) = disposables + toDoRepository
        .deleteToDo(todo.id)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            _uiEvent.value = CanBeRestored(todo)
        }


    fun addToDO(todo: ToDo) = disposables + toDoRepository
        .addToDo(todo)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()


    fun setSearchBy(searchBy: ToDoSearchBy) {
        toDoRepository.setSearchBy(searchBy)
    }

    fun setSettings(appSettings: AppSettings) = disposables + toDoRepository
        .setSettings(appSettings)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()


    fun deleteAllToDos() = disposables + toDoRepository
        .deleteAllToDos()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()

}
