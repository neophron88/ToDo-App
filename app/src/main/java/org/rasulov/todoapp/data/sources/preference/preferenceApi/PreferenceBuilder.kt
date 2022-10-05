package org.rasulov.todoapp.data.sources.preference.preferenceApi

import android.content.Context

class PreferenceBuilder(
    private val appContext: Context
) {

    fun build(name: String): Preference {
        val preferences = appContext.getSharedPreferences(
            name,
            Context.MODE_PRIVATE
        )
        return PreferenceImpl(preferences)
    }

}