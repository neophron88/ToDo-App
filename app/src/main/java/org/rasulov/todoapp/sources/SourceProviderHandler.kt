package org.rasulov.todoapp.sources

import android.content.Context
import org.rasulov.todoapp.sources.persistence_source.PersistenceSourceProvider
import org.rasulov.todoapp.sources.persistence_source.preference.ToDoPreference
import org.rasulov.todoapp.sources.persistence_source.room.ToDoDatabase

class SourceProviderHandler {

    companion object {
        fun getPersistenceSourceProvider(appContext: Context) =
            PersistenceSourceProvider(
                ToDoDatabase.getDatabase(appContext),
                ToDoPreference.getPreference(appContext)
            )
    }

}