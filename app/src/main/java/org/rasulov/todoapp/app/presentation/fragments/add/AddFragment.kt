package org.rasulov.todoapp.app.presentation.fragments.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.rasulov.todoapp.R
import org.rasulov.todoapp.databinding.FragmentAddBinding
import org.rasulov.todoapp.app.presentation.utils.addMenuProvider
import org.rasulov.todoapp.app.presentation.utils.viewBindings


class AddFragment : Fragment(R.layout.fragment_add) {

    private val binding: FragmentAddBinding by viewBindings()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMenuProvider(R.menu.add_fragment) { false }
    }
}