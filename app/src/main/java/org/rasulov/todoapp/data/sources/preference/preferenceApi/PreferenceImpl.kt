package org.rasulov.todoapp.data.sources.preference.preferenceApi

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext


class PreferenceImpl(private val preferences: SharedPreferences) : Preference {

    private val settingsFlow = MutableSharedFlow<Unit>(
        1,
        0,
        BufferOverflow.DROP_OLDEST
    ).also { it.tryEmit(Unit) }


    override fun getSettings(): Flow<Map<String, *>> {
        return settingsFlow
            .map { preferences.all }
            .flowOn(Dispatchers.IO)
    }


    override suspend fun setSetting(setting: Setting) {
        withContext(Dispatchers.IO) {
            setting.setup(preferences.edit()).commit()
            settingsFlow.emit(Unit)
        }
    }


    override suspend fun setSettings(settings: List<Setting>) {
        withContext(Dispatchers.IO) {
            val editor = preferences.edit()
            settings.forEach { it.setup(editor) }
            editor.commit()
            settingsFlow.emit(Unit)
        }
    }
}

