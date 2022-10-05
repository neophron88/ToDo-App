package org.rasulov.todoapp.domain.entities

import org.rasulov.todoapp.domain.EmptyFieldException

data class ToDo(
    val id: Long,
    val title: String,
    val priority: Priority,
    val description: String
) {
    fun validate() {
        if (title.isBlank()) throw EmptyFieldException()
    }

    companion object {
        const val DEFAULT = 0L
    }
}
