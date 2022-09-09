package org.rasulov.todoapp.app.presentation.fragments.add

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.rasulov.androidx.fragment.addMenuProvider
import org.rasulov.androidx.fragment.getGradientDrawable
import org.rasulov.androidx.fragment.viewBindings
import org.rasulov.todoapp.R
import org.rasulov.todoapp.app.domain.Repository
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.presentation.utils.*
import org.rasulov.todoapp.databinding.FragmentAddBinding
import javax.inject.Inject

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {

    private val viewModel: AddViewModel by viewModels()

    private val binding: FragmentAddBinding by viewBindings()

    private val controller by lazy { findNavController() }

    private val colors by lazy {
        requireContext().getColorsFromRes(R.array.colors)
    }

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var repository2: Repository


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("it0088", "onViewCreated: $repository")
        Log.d("it0088", "onViewCreated: $repository2")

        addMenuProvider()

        setSpinnerOnItemSelected()

        observeUiEvent()
    }


    private fun addMenuProvider() = addMenuProvider(
        menuRes = R.menu.add_fragment,
        onMenuItemSelected = {
            when (it.itemId) {
                R.id.menu_add -> viewModel.addToDo(getToDo())
                android.R.id.home -> controller.popBackStack()
            }
        }
    )

    private fun getToDo(): ToDo {
        binding.toDoViews.apply {
            val title = edtTitle.text.toString()
            val priority = Priority.values()[spinnerPriority.selectedItemPosition + 1]
            val description = edtDescription.text.toString()
            return ToDo(ToDo.DEFAULT, title, priority, description)
        }
    }


    private fun setSpinnerOnItemSelected() {
        binding.toDoViews.spinnerPriority.setOnItemSelectedListener { item, position ->
            val drawable = getGradientDrawable(R.drawable.spinner_shape)
            drawable.setStroke((2).dpToPixel(requireContext()), colors[position])
            (item as? TextView)?.setTextColor(colors[position])
            binding.toDoViews.spinnerPriority.background = drawable
        }
    }


    private fun observeUiEvent() {
        viewModel.uiEvent.observe(viewLifecycleOwner) {
            when (it) {
                EmptyFieldUIEvent -> showToast()
                OperationSuccessUIEvent -> controller.popBackStack()
            }
        }
    }

    private fun showToast() {
        Toast.makeText(
            requireContext().applicationContext,
            "The Title field is empty",
            Toast.LENGTH_LONG
        ).show()
    }
}