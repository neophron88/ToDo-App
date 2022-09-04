package org.rasulov.todoapp.app.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupActionBarWithNavController
import org.rasulov.androidx.activity.viewBindings
import org.rasulov.todoapp.R
import org.rasulov.todoapp.app.Singletons
import org.rasulov.todoapp.app.presentation.utils.navControllers
import org.rasulov.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBindings()

    private val navController by navControllers(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Singletons.init(applicationContext)
        setContentView(binding.root)
        setupActionBarWithNavController(navController)
    }

}