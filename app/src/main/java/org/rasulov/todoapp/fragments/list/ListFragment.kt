package org.rasulov.todoapp.fragments.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.rasulov.todoapp.R
import org.rasulov.todoapp.databinding.FragmentListBinding
import org.rasulov.todoapp.utils.addMenuProvider
import org.rasulov.todoapp.utils.viewBindings


class ListFragment : Fragment(R.layout.fragment_list) {

    private val binding: FragmentListBinding by viewBindings()

    private val controller by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            controller.navigate(R.id.action_listFragment_to_addFragment)
        }

        addMenuProvider(R.menu.list_fragment) {
            false
        }
    }
}