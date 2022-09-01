package org.rasulov.todoapp.sources.persistence_source.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo

@Entity(tableName = "todo_table")
data class ToDoDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val priority: Int,
    val description: String
) {

    fun toToDo() = ToDo(
        title,
        Priority.values()[priority],
        description
    )

}