package org.rasulov.todoapp.presentation.fragments.add

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import dagger.hilt.android.AndroidEntryPoint
import org.rasulov.todoapp.utilities.fragment.addMenuProvider
import org.rasulov.todoapp.utilities.fragment.disableTransitionOverlap
import org.rasulov.todoapp.utilities.fragment.getGradientDrawable
import org.rasulov.todoapp.utilities.fragment.viewBindings
import org.rasulov.todoapp.R
import org.rasulov.todoapp.domain.entities.Priority
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.presentation.utils.*
import org.rasulov.todoapp.databinding.FragmentAddBinding
import org.rasulov.todoapp.utilities.primitives.dp

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {

    private val viewModel: AddViewModel by viewModels()

    private val binding: FragmentAddBinding by viewBindings()

    private val controller by lazy { findNavController() }

    private val colors by lazy {
        requireContext().getColors(R.array.colors)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Slide(Gravity.END).apply { duration = 300 }
        returnTransition = Slide(Gravity.END).apply { duration = 300 }
        disableTransitionOverlap()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMenu()

        setSpinnerOnItemSelected()

        observeUiEvent()

    }


    private fun addMenu() = addMenuProvider(
        menuRes = R.menu.add_fragment,
        onMenuItemSelected = {
            when (it.itemId) {
                R.id.menu_add -> viewModel.addToDo(getToDo())
                android.R.id.home -> controller.popBackStack()
            }
        }
    )

    private fun getToDo(): ToDo = with(binding.toDoViews) {
        val title = edtTitle.text.toString()
        val priority = Priority.values()[spinnerPriority.selectedItemPosition + 1]
        val description = edtDescription.text.toString()
        return ToDo(ToDo.DEFAULT, title, priority, description)
    }



    private fun setSpinnerOnItemSelected() {
        binding.toDoViews.spinnerPriority.setOnItemSelectedListener { item, position ->
            val drawable = requireContext().getGradientDrawable(R.drawable.spinner_shape)
            drawable.setStroke(2.dp(requireContext()), colors[position])
            (item as? TextView)?.setTextColor(colors[position])
            binding.toDoViews.spinnerPriority.background = drawable

        }
    }


    private fun observeUiEvent() = viewModel.uiEvent.observe(viewLifecycleOwner) {
        when (it) {
            EmptyField -> showLongToast("The Title field is empty")
            OperationSuccess -> controller.popBackStack()
            else -> throw IllegalStateException("$it event is not handled here!")
        }
    }


}