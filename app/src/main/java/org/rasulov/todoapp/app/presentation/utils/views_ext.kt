package org.rasulov.todoapp.app.presentation.utils

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

typealias SelectedListener = (view: View?, position: Int) -> Unit

fun Spinner.setOnItemSelectedListener(listener: SelectedListener) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            listener(view, position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}