package org.rasulov.todoapp.app.presentation.fragments.list.recyclerView_configs

import androidx.recyclerview.widget.DiffUtil
import org.rasulov.todoapp.app.domain.entities.ToDo

class ToDoDiffUtil(
) : DiffUtil.ItemCallback<ToDo>() {

    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem == newItem
    }


}