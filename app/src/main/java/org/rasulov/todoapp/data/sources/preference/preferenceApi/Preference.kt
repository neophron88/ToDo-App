package org.rasulov.todoapp.data.sources.preference.preferenceApi

import io.reactivex.Completable
import io.reactivex.Flowable

interface Preference {

    fun getSettings(): Flowable<Map<String, *>>

    fun setSetting(setting: Setting): Completable

    fun setSettings(settings: List<Setting>): Completable
}

