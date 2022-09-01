package org.rasulov.todoapp.sources.persistence_source

import org.rasulov.todoapp.app.domain.entities.Settings
import org.rasulov.todoapp.app.domain.entities.ToDoSearchBy

data class FindBy(
    val searchBy: ToDoSearchBy,
    val settings: Settings
)
