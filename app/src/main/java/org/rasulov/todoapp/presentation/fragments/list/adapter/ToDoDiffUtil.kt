package org.rasulov.todoapp.presentation.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import org.rasulov.todoapp.domain.entities.ToDo

class ToDoDiffUtil(
) : DiffUtil.ItemCallback<ToDo>() {

    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem == newItem
    }


}