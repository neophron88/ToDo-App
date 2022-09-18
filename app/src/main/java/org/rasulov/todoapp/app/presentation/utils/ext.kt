package org.rasulov.todoapp.app.presentation.utils

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.annotation.ArrayRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.Settings
import org.rasulov.todoapp.app.presentation.fragments.list.ListViewModel

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


fun AppCompatActivity.navControllers(@IdRes layout: Int) = lazy {
    val navHostFragment = supportFragmentManager.findFragmentById(layout) as NavHostFragment
    navHostFragment.navController
}


fun Context.getColorsFromRes(@ArrayRes arrayRes: Int): List<Int> {
    return resources
        .getIntArray(arrayRes).toList()
}

fun Int.dpToPixel(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}


typealias OnQueryListener = (text: String) -> Unit

fun SearchView.setOnQueryListener(onQueryListener: OnQueryListener) {
    isSubmitButtonEnabled = false

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?) = false

        override fun onQueryTextChange(newText: String?): Boolean {
            Log.d("it0088", "onQueryTextChange: $newText")
            val text = newText ?: return false
            onQueryListener(text)
            return true
        }
    })
}

fun ListViewModel.setPriority(priority: Priority) {
    setSettings(Settings(priority))
}

