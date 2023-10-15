package org.rasulov.todoapp.data.sources.preference.preferenceApi

import android.content.SharedPreferences
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject


class PreferenceImpl(private val preferences: SharedPreferences) : Preference {

    private val settingsFlow = BehaviorSubject.createDefault(Unit)


    override fun getSettings(): Flowable<Map<String, *>> {
        return settingsFlow
            .map { preferences.all }
            .subscribeOn(Schedulers.io())
            .toFlowable(BackpressureStrategy.LATEST)
    }


    override fun setSetting(setting: Setting): Completable =
        Completable.fromAction {
            setting.setup(preferences.edit()).commit()
            settingsFlow.onNext(Unit)
        }.observeOn(Schedulers.io())


    override fun setSettings(settings: List<Setting>): Completable =
        Completable.fromAction {
            val editor = preferences.edit()
            settings.forEach { it.setup(editor) }
            editor.commit()
            settingsFlow.onNext(Unit)
        }.observeOn(Schedulers.io())

}

