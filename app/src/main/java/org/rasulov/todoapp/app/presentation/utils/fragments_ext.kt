package org.rasulov.todoapp.app.presentation.utils

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.ArrayRes
import androidx.annotation.MenuRes
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import org.rasulov.todoapp.R

typealias onMenuItemSelected = (item: MenuItem) -> Boolean

fun Fragment.addMenuProvider(
    @MenuRes menuRes: Int,
    listener: onMenuItemSelected
) {

    val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuRes, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return listener.invoke(menuItem)
        }
    }

    requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.STARTED)
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