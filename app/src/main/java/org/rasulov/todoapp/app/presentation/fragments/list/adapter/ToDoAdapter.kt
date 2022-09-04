package org.rasulov.todoapp.app.presentation.fragments.list.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.rasulov.todoapp.R
import org.rasulov.todoapp.app.domain.entities.ToDo
import org.rasulov.androidx.adapter.AdvancedAdapter
import org.rasulov.todoapp.app.presentation.utils.getColorsFromRes
import org.rasulov.todoapp.databinding.TodoItemBinding


class ToDoAdapter(
    private val onClick: OnClickListener
) : AdvancedAdapter<ToDo, ToDoAdapter.ToDoHolder>() {

    private var colorsForPriority: List<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoItemBinding.inflate(inflater, parent, false)
        colorsForPriority = parent.context.getColorsFromRes(R.array.colors)
        binding.root.setOnClickListener { onClick.onItemClick(it.tag as ToDo) }
        return ToDoHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        val todo = getItem(position)
        holder.bnd.apply {
            root.tag = todo
            title.text = todo.title
            val color = colorsForPriority?.get(todo.priority.ordinal - 1) ?: 0
            priorityIndicator.backgroundTintList = ColorStateList.valueOf(color)
            description.text = todo.description
        }
    }


    class ToDoHolder(
        val bnd: TodoItemBinding
    ) : RecyclerView.ViewHolder(bnd.root)

    interface OnClickListener {
        fun onItemClick(toDo: ToDo)
    }

}

