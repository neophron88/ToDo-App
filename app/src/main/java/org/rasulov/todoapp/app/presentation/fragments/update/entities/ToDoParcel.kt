package org.rasulov.todoapp.app.presentation.fragments.update.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo

@Parcelize
data class ToDoParcel(
    val id: Long,
    val title: String,
    val priority: Priority,
    val description: String
) : Parcelable {

    companion object {
        fun fromToDo(todo: ToDo) = ToDoParcel(
            id = todo.id,
            title = todo.title,
            priority = todo.priority,
            description = todo.description
        )
    }


}
