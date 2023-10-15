package org.rasulov.todoapp.data.sources.preference

import io.reactivex.Completable
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.rasulov.todoapp.data.sources.preference.preferenceApi.Preference
import org.rasulov.todoapp.data.sources.preference.preferenceApi.Setting
import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.domain.entities.Priority

class PreferenceToDoSourceImpl(
    private val preference: Preference
) : PreferenceToDoSource {

    override fun setAppSettings(appSettings: AppSettings): Completable {
        val list: List<Setting> = listOf(
            Setting.IntValue("priority", appSettings.priority.ordinal)
        )
        return preference.setSettings(list)
    }

    override fun getAppSettings(): Flowable<AppSettings> {
        return preference.getSettings().map {
            val value = (it["priority"] ?: Priority.NONE.ordinal) as Int
            val priority = Priority.values()[value]
            AppSettings(priority)
        }
    }
}