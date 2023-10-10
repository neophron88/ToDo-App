package org.rasulov.todoapp.presentation.fragments.list.viewholders

import android.content.res.ColorStateList
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.databinding.TodoItemBinding
import org.rasulov.todoapp.utilities.rv_adapter_delegate.ItemViewHolder


class ToDoHolder(
    view: View,
    private val colorsForPriority: List<Int>,
    private val onClick: OnClickListener,
) : ItemViewHolder<ToDo>(view) {

    val binding = TodoItemBinding.bind(view)

    init { setupOnClickListener() }

    override fun onBind(item: ToDo) = with(binding) {
        ViewCompat.setTransitionName(title, "${item.id}title")
        ViewCompat.setTransitionName(description, "${item.id}description")
        ViewCompat.setTransitionName(priorityIndicator, "${item.id}priority")
        title.text = item.title
        val color = colorsForPriority[item.priority.ordinal - 1]
        priorityIndicator.backgroundTintList = ColorStateList.valueOf(color)
        description.text = item.description
    }

    private fun setupOnClickListener() =
        binding.root.setOnClickListener { onClick.onItemClick(this) }

}

fun RecyclerView.ViewHolder.asToDoHolder() = this as ToDoHolder

interface OnClickListener {
    fun onItemClick(holder: ToDoHolder)
}
