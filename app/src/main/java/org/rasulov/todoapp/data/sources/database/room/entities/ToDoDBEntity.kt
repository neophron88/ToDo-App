package org.rasulov.todoapp.data.sources.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.rasulov.todoapp.domain.entities.Priority
import org.rasulov.todoapp.domain.entities.ToDo

@Entity(tableName = "todo_table")
data class ToDoDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val priority: Int,
    val description: String
) {

    fun toToDo() = ToDo(
        id,
        title,
        Priority.values()[priority],
        description
    )


    companion object {
        fun fromToDo(todo: ToDo) = ToDoDBEntity(
            id = todo.id,
            title = todo.title,
            priority = todo.priority.ordinal,
            description = todo.description
        )
    }

}