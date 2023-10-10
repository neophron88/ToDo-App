package org.rasulov.todoapp.testutils

import org.rasulov.todoapp.domain.entities.Priority
import org.rasulov.todoapp.domain.entities.ToDo

fun generateToDoList() = listOf(
    ToDo(1, "Clean Room 1", Priority.HIGH, "Very good clean room 1"),
    ToDo(2, "Clean Room 2", Priority.MEDIUM, "Very good clean room 2"),
    ToDo(3, "Clean Room 3", Priority.LOW, "Very good clean room 3"),
    ToDo(4, "Clean Room 4", Priority.NONE, "Very good clean room 4"),
)

fun generateToDo(
    id: Long = 1,
    title: String = "Clean Room 1",
    priority: Priority = Priority.HIGH,
    description: String = "Very good clean room 1"
) = ToDo(id, title, priority, description)