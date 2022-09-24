package org.rasulov.todoapp.app.presentation.fragments.list.adapter

import android.content.res.ColorStateList
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.todoapp.databinding.TodoItemBinding
import java.lang.IllegalStateException

class ToDoHolder(
    val bnd: TodoItemBinding,
    private val colorsForPriority: List<Int>
) : RecyclerView.ViewHolder(bnd.root) {

    private var _todo: ToDo? = null
    val todo: ToDo
        get() = _todo ?: throw IllegalStateException(
            "After onBindViewHolder field shouldn't be null"
        )

    fun bind(todo: ToDo) {
        bnd.apply {
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


}
