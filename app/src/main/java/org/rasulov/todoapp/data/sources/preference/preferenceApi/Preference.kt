package org.rasulov.todoapp.data.sources.preference.preferenceApi

import kotlinx.coroutines.flow.Flow

interface Preference {

    fun getSettings(): Flow<Map<String, *>>

    suspend fun setSetting(setting: Setting)

    suspend fun setSettings(settings: List<Setting>)
}

