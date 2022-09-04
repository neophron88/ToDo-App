package org.rasulov.todoapp.sources.persistence_source.preference

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.Settings
import java.security.Key


class ToDoPrefAccessorImpl(private val preferences: SharedPreferences) : ToDoPrefAccessor {

    private val settingsFlow = MutableSharedFlow<Unit>(
        1,
        0,
        BufferOverflow.DROP_OLDEST
    ).also { it.tryEmit(Unit) }


    override fun getSettings(): Flow<Settings> {
        return settingsFlow
            .map { obtainSettings() }
            .flowOn(Dispatchers.IO)

    }

    private fun obtainSettings(): Settings {
        val ordinal = preferences.getInt(KEY, Priority.NONE.ordinal)
        return Settings(
            Priority.values()[ordinal]
        )
    }

    override suspend fun setSettings(settings: Settings) {
        withContext(Dispatchers.IO) {
            val isCommitted = preferences.edit()
                .putInt(KEY, settings.priority.ordinal)
                .commit()
            if (isCommitted) settingsFlow.emit(Unit)
        }
    }

    companion object {
        private const val KEY = "key"
    }
}