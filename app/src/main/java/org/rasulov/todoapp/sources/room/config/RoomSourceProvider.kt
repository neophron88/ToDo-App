package org.rasulov.todoapp.sources.room.config

import org.rasulov.todoapp.app.data.ToDoSource
import org.rasulov.todoapp.sources.room.api.RoomToDoSource

class RoomSourceProvider(
    private val toDoDatabase: ToDoDatabase
) {

    fun getToDoSource(): ToDoSource {
        return RoomToDoSource(toDoDatabase.getToDoDao())
    }
}