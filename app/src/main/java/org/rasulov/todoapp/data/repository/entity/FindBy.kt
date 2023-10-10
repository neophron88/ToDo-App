package org.rasulov.todoapp.data.repository.entity

import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.domain.entities.ToDoSearchBy

//This class should be located on sources package instead of repository
data class FindBy(
    val searchBy: ToDoSearchBy,
    val appSettings: AppSettings
)