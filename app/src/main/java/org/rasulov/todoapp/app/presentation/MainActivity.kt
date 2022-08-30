package org.rasulov.todoapp.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.setupActionBarWithNavController
import org.rasulov.todoapp.R
import org.rasulov.todoapp.app.Singletons
import org.rasulov.todoapp.app.presentation.utils.viewBindings
import org.rasulov.todoapp.databinding.ActivityMainBinding
import org.rasulov.todoapp.app.presentation.utils.navControllers

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBindings()

    private val navController by navControllers(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Singletons.init(applicationContext)
        setContentView(binding.root)
        setupActionBarWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}