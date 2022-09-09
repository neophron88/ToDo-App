package org.rasulov.todoapp.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.rasulov.androidx.activity.viewBindings
import org.rasulov.todoapp.R
import org.rasulov.todoapp.app.presentation.utils.navControllers
import org.rasulov.todoapp.databinding.ActivityMainBinding

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