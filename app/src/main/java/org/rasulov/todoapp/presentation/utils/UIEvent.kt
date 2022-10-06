package org.rasulov.todoapp.presentation.utils

import org.rasulov.todoapp.domain.entities.ToDo

sealed class UIEvent

object EmptyField : UIEvent()

object OperationSuccess : UIEvent()

object OperationFailed : UIEvent()

class CanBeRestored(val toDo: ToDo) : UIEvent()






