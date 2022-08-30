package org.rasulov.todoapp.app.presentation.fragments.update

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.rasulov.todoapp.R
import org.rasulov.todoapp.databinding.FragmentAddBinding
import org.rasulov.todoapp.app.presentation.utils.viewBindings



class UpdateFragment : Fragment(R.layout.fragment_update) {

    private val binding: FragmentAddBinding by viewBindings()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}