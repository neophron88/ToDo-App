package org.rasulov.todoapp.app.presentation.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.rasulov.todoapp.R
import org.rasulov.todoapp.app.Singletons
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.databinding.FragmentListBinding
import org.rasulov.todoapp.app.presentation.utils.addMenuProvider
import org.rasulov.todoapp.app.presentation.utils.viewBindings
import org.rasulov.todoapp.app.presentation.utils.viewModel


class ListFragment : Fragment(R.layout.fragment_list) {

    private val binding: FragmentListBinding by viewBindings()

    private val controller by lazy { findNavController() }

    private val viewModel by viewModel {
        ListViewModel(Singletons.toDoRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            viewModel.setSearchBy(Priority.LOW)
        }

        addMenuProvider(R.menu.list_fragment) {
            false
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.allToDos.collectLatest {
                    Log.d("it0088", "$it")
                }
            }

        }
    }
}