package org.rasulov.todoapp.app.presentation.fragments.update

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.rasulov.todoapp.R
import org.rasulov.todoapp.app.Singletons
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.presentation.utils.*
import org.rasulov.todoapp.databinding.FragmentAddBinding


class UpdateFragment : Fragment(R.layout.fragment_update) {

    private val viewModel by viewModel {
        UpdateViewModel(Singletons.toDoRepository)
    }

    private val binding: FragmentAddBinding by viewBindings()

    private val controller by lazy { findNavController() }

    private val args by navArgs<UpdateFragmentArgs>()

    private val colors by lazy {
        requireContext().getColorsFromRes(R.array.colors)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toDoViews.apply {
            edtTitle.setText(args.item.title)
            spinnerPriority.setSelection(args.item.priority.ordinal - 1)
            edtDescription.setText(args.item.description)
        }


        addMenuProvider(
            menuRes = R.menu.update_fragment,
            onMenuItemSelected = {
                when (it.itemId) {
                    R.id.menu_save -> viewModel.updateToDO(getToDo())
                    R.id.menu_delete -> viewModel.deleteToDO(args.item.id)
                }
                controller.popBackStack()
            }
        )


        binding.toDoViews.spinnerPriority.setOnItemSelectedListener { item, position ->
            val drawable = getGradientDrawable(R.drawable.spinner_shape)
            drawable.setStroke((2).dpToPixel(requireContext()), colors[position])
            (item as? TextView)?.setTextColor(colors[position])
            binding.toDoViews.spinnerPriority.background = drawable
        }
    }


    private fun getToDo(): ToDo {
        binding.toDoViews.apply {
            val title = edtTitle.text.toString()
            val priority = Priority.values()[spinnerPriority.selectedItemPosition + 1]
            val description = edtDescription.text.toString()
            return ToDo(args.item.id, title, priority, description)
        }

    }
}