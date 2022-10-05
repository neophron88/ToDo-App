package org.rasulov.todoapp.presentation.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.databinding.TodoItemBinding


class ToDoAdapter(
    private val onClick: OnClickListener,
    private val colorsForPriority: List<Int>
) : ListAdapter<ToDo, ToDoHolder>(ToDoDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoItemBinding.inflate(inflater, parent, false)
        val holder = ToDoHolder(binding, colorsForPriority)
        binding.root.setOnClickListener { onClick.onItemClick(holder) }
        return holder
    }

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }


    interface OnClickListener {
        fun onItemClick(holder: ToDoHolder)
    }

}

