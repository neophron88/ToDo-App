package org.rasulov.todoapp.sources.persistence_source.preference

import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.app.domain.entities.Settings

interface ToDoPrefAccessor {

    fun getSettings(): Flow<Settings>

    suspend fun setSettings(settings: Settings)
}