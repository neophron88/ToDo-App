package org.rasulov.todoapp.utils

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

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

    requireActivity().addMenuProvider(
        menuProvider,
        viewLifecycleOwner,
        Lifecycle.State.STARTED
    )
}