package org.rasulov.todoapp.data.sources.preference

import io.reactivex.Completable
import io.reactivex.Flowable
import org.rasulov.todoapp.domain.entities.AppSettings

interface PreferenceToDoSource {

    fun setAppSettings(appSettings: AppSettings): Completable

    fun getAppSettings(): Flowable<AppSettings>
}