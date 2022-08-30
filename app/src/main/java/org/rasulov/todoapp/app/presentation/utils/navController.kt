package org.rasulov.todoapp.app.presentation.utils

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment


fun AppCompatActivity.navControllers(@IdRes layout: Int) = lazy {
    val navHostFragment = supportFragmentManager.findFragmentById(layout) as NavHostFragment
    navHostFragment.navController
}
