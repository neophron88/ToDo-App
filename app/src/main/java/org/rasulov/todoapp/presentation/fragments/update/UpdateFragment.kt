package org.rasulov.todoapp.presentation.fragments.update

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.ChangeBounds
import androidx.transition.Slide
import dagger.hilt.android.AndroidEntryPoint
import org.rasulov.utilities.fragment.addMenuProvider
import org.rasulov.utilities.fragment.disableTransitionOverlap
import org.rasulov.utilities.fragment.getGradientDrawable
import org.rasulov.utilities.fragment.viewBindings
import org.rasulov.todoapp.R
import org.rasulov.todoapp.domain.entities.Priority
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.presentation.utils.*
import org.rasulov.todoapp.databinding.FragmentUpdateBinding
import org.rasulov.utilities.primitives.dp

@AndroidEntryPoint
class UpdateFragment : Fragment(R.layout.fragment_update) {

    private val viewModel: UpdateViewModel by viewModels()

    private val binding: FragmentUpdateBinding by viewBindings()

    private val controller by lazy { findNavController() }

    private val args by navArgs<UpdateFragmentArgs>()

    private val colors by lazy {
        requireContext().getColors(R.array.colors)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = ChangeBounds().apply { duration = 400 }
        returnTransition = Slide(Gravity.END).apply { duration = 300 }
        disableTransitionOverlap()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()

        addMenu()

        setSpinnerOnItemSelected()

        observeUiEvent()

    }

    private fun setUpViews() = binding.toDoViews.apply {
        val item = args.item
        edtTitle.setText(item.title)
        spinnerPriority.setSelection(item.priority.ordinal - 1)
        edtDescription.setText(item.description)
    }


    private fun addMenu() = addMenuProvider(
        menuRes = R.menu.update_fragment,
        onMenuItemSelected = {
            when (it.itemId) {
                R.id.menu_save -> viewModel.updateToDO(getToDo())
                R.id.menu_delete -> viewModel.deleteToDO(args.item.id)
                android.R.id.home -> controller.popBackStack()
            }
        }
    )


    private fun getToDo(): ToDo = with(binding.toDoViews) {
        val title = edtTitle.text.toString()
        val priority = Priority.values()[spinnerPriority.selectedItemPosition + 1]
        val description = edtDescription.text.toString()
        return ToDo(args.item.id, title, priority, description)
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