@file:Suppress("unused")

package org.rasulov.todoapp.data.sources.preference.preferenceApi

import android.content.SharedPreferences

abstract class Setting {


    abstract fun setup(editor: SharedPreferences.Editor): SharedPreferences.Editor


    class IntValue(
        private val key: String,
        private val value: Int
    ) : Setting() {

        override fun setup(editor: SharedPreferences.Editor): SharedPreferences.Editor {
            return editor.putInt(key, value)
        }
    }


    class StringValue(
        private val key: String,
        private val value: String
    ) : Setting() {

        override fun setup(editor: SharedPreferences.Editor): SharedPreferences.Editor {
            return editor.putString(key, value)
        }
    }

    class Clear : Setting() {

        override fun setup(editor: SharedPreferences.Editor): SharedPreferences.Editor {
            return editor.clear()
        }
    }

    class Remove(
        private val key: String,
    ) : Setting() {

        override fun setup(editor: SharedPreferences.Editor): SharedPreferences.Editor {
            return editor.remove(key)
        }
    }

}
