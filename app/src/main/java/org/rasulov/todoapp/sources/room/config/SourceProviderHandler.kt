package org.rasulov.todoapp.sources.room.config

import android.content.Context

class SourceProviderHandler {

    companion object {
        fun getRoomSourceProvider(appContext: Context) =
            RoomSourceProvider(ToDoDatabase.getDatabase(appContext))
    }

}