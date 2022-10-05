package org.rasulov.todoapp.data.sources.preference

import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.domain.entities.AppSettings

interface PreferenceToDoSource {

    suspend fun setAppSettings(appSettings: AppSettings)

    fun getAppSettings(): Flow<AppSettings>
}