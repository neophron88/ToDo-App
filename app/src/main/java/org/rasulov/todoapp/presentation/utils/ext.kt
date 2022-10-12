package org.rasulov.todoapp.presentation.utils

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import org.rasulov.todoapp.domain.entities.Priority
import org.rasulov.todoapp.domain.entities.AppSettings
import org.rasulov.todoapp.presentation.fragments.list.ListViewModel

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


fun Context.getColors(@ArrayRes arrayRes: Int): List<Int> {
    return resources.getIntArray(arrayRes).toList()
}


typealias OnQueryListener = (text: String) -> Unit

fun SearchView.setOnQueryListener(onQueryListener: OnQueryListener) {
    isSubmitButtonEnabled = false

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?) = false

        override fun onQueryTextChange(newText: String?): Boolean {
            val text = newText ?: return false
            onQueryListener(text)
            return true
        }
    })
}

fun ListViewModel.setPriority(priority: Priority) {
    setSettings(AppSettings(priority))
}

fun Fragment.showLongToast(text: String) {
    Toast.makeText(
        requireContext().applicationContext,
        text,
        Toast.LENGTH_LONG
    ).show()
}

