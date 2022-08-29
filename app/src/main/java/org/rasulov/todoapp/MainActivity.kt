package org.rasulov.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import org.rasulov.todoapp.utils.viewBindings
import org.rasulov.todoapp.databinding.ActivityMainBinding
import org.rasulov.todoapp.utils.navControllers

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBindings()

    private val navController by navControllers(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}