package org.rasulov.todoapp.app.domain.entities

import org.rasulov.todoapp.app.domain.exceptions.EmptyFieldException

data class ToDo(
    val title: String,
    val priority: Priority,
    val description: String
) {
    fun validate() {
        if (title.isBlank()) throw EmptyFieldException()
        if (description.isBlank()) throw EmptyFieldException()
    }
}
