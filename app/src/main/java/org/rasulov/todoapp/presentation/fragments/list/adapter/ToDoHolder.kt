package org.rasulov.todoapp.presentation.fragments.list.adapter

import android.content.res.ColorStateList
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.databinding.TodoItemBinding
import java.lang.IllegalStateException

class ToDoHolder(
    val binding: TodoItemBinding,
    private val colorsForPriority: List<Int>
) : RecyclerView.ViewHolder(binding.root) {

    private var _todo: ToDo? = null
    val todo: ToDo
        get() = _todo ?: throw IllegalStateException(
            "After onBindViewHolder field shouldn't be null"
        )

    fun bind(todo: ToDo) = binding.apply {
        _todo = todo
        ViewCompat.setTransitionName(title, "${todo.id}title")
        ViewCompat.setTransitionName(description, "${todo.id}description")
        ViewCompat.setTransitionName(priorityIndicator, "${todo.id}priority")
        title.text = todo.title
        val color = colorsForPriority[todo.priority.ordinal - 1]
        priorityIndicator.backgroundTintList = ColorStateList.valueOf(color)
        description.text = todo.description
    }
}

fun RecyclerView.ViewHolder.asToDoHolder() = this as ToDoHolder

