package org.rasulov.todoapp.app.presentation.fragments.add

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.rasulov.todoapp.R
import org.rasulov.todoapp.app.Singletons
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.app.presentation.utils.*
import org.rasulov.todoapp.databinding.FragmentAddBinding


class AddFragment : Fragment(R.layout.fragment_add) {

    private val binding: FragmentAddBinding by viewBindings()

    private val colors by lazy {
        requireContext().getColorsFromRes(R.array.colors)
    }


    private val viewModel by viewModel {
        AddViewModel(Singletons.toDoRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMenuProvider(R.menu.add_fragment) {
            when (it.itemId) {
                R.id.menu_add -> {
                    viewModel.addToDo(getToDo())
                    true
                }
                else -> false
            }
        }

        binding.toDoViews.spinnerPriority.setOnItemSelectedListener { view, position ->
            val drawable = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.spinner_shape
            ) as GradientDrawable

            drawable.setStroke((2).dpToPixel(requireContext()), colors[position])
            (view as? TextView)?.setTextColor(colors[position])
            binding.toDoViews.spinnerPriority.background = drawable

        }
    }

    private fun getToDo(): ToDo {
        binding.toDoViews.apply {
            val title = edtTitle.text.toString()

            val priority = Priority.values()[spinnerPriority.selectedItemPosition + 1]
            val description = edtDescription.text.toString()
            return ToDo(title, priority, description)
        }

    }
}