package org.rasulov.todoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.rasulov.todoapp.R
import org.rasulov.todoapp.databinding.ActivityMainBinding
import org.rasulov.todoapp.presentation.utils.navControllers
import org.rasulov.todoapp.utilities.activity.viewBindings

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val binding: ActivityMainBinding by viewBindings()

    private val navController by navControllers(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActionBarWithNavController(navController)

    }
}