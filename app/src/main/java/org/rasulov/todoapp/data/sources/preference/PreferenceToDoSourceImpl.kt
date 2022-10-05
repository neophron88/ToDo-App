package org.rasulov.todoapp.data.sources.preference

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.rasulov.todoapp.domain.entities.Priority
import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.data.sources.preference.preferenceApi.Preference
import org.rasulov.todoapp.data.sources.preference.preferenceApi.Setting

class PreferenceToDoSourceImpl(
    private val preference: Preference
) : PreferenceToDoSource {

    override suspend fun setAppSettings(appSettings: AppSettings) {
        val list: List<Setting> = listOf(
            Setting.IntValue("priority", appSettings.priority.ordinal)
        )
        preference.setSettings(list)
    }

    override fun getAppSettings(): Flow<AppSettings> {
        return preference.getSettings().map {
            val value = (it["priority"] ?: Priority.NONE.ordinal) as Int
            val priority = Priority.values()[value]
            AppSettings(priority)
        }
    }
}