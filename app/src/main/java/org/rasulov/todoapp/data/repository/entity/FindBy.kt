package org.rasulov.todoapp.data.repository.entity

import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.domain.entities.ToDoSearchBy

data class FindBy(
    val searchBy: ToDoSearchBy,
    val appSettings: AppSettings
)