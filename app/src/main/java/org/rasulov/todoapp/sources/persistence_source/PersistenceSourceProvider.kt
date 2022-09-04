package org.rasulov.todoapp.sources.persistence_source

import org.rasulov.todoapp.app.data.todo.ToDoSource
import org.rasulov.todoapp.sources.persistence_source.preference.ToDoPreference
import org.rasulov.todoapp.sources.persistence_source.room.ToDoDatabase

class PersistenceSourceProvider(
    private val toDoDatabase: ToDoDatabase,
    private val toDoPreference: ToDoPreference
) {

    fun getToDoSource(): ToDoSource {
        return PersistenceToDoSource(
            toDoDatabase.getToDoDao(),
            toDoPreference.getToDoPrefAccessor()
        )
    }
}