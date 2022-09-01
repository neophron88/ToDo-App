package org.rasulov.todoapp.sources.persistence_source.preference

import android.content.Context
import android.content.SharedPreferences

class ToDoPreference(
    private val sharedPreferences: SharedPreferences
) {

    fun getToDoPrefAccessor(): ToDoPrefAccessor {
        return ToDoPrefAccessorImpl(sharedPreferences)
    }


    companion object {
        fun getPreference(appContext: Context): ToDoPreference {
            return ToDoPreference(
                appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
            )
        }
    }
}