package org.rasulov.todoapp.presentation.fragments.list.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import org.rasulov.todoapp.domain.entities.ToDo
import org.rasulov.todoapp.databinding.TodoItemBinding
import org.rasulov.utilities.rv_adapter_delegate.ItemDelegate
import org.rasulov.utilities.rv_adapter_delegate.ItemViewHolder
import java.lang.IllegalStateException


class ToDoItemDelegate(
    private val onClick: OnClickListener,
    private val colorsForPriority: List<Int>
) : ItemDelegate<ToDo>(ToDo::class) {

    override fun createViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ToDo> {
        val binding = TodoItemBinding.inflate(layoutInflater, parent, false)
        val holder = ToDoHolder(binding, colorsForPriority)
        binding.root.setOnClickListener { onClick.onItemClick(holder) }
        return holder
    }

    override fun areItemsTheSame(
        oldItem: ToDo, newItem: ToDo
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ToDo, newItem: ToDo
    ): Boolean = oldItem == newItem

}

class ToDoHolder(
    val binding: TodoItemBinding,
    private val colorsForPriority: List<Int>
) : ItemViewHolder<ToDo>(binding.root) {

    lateinit var todo: ToDo

    override fun bind(it: ToDo?) = with(binding) {
        val item = it ?: return@with
        todo = item
        ViewCompat.setTransitionName(title, "${item.id}title")
        ViewCompat.setTransitionName(description, "${item.id}description")
        ViewCompat.setTransitionName(priorityIndicator, "${item.id}priority")
        title.text = item.title
        val color = colorsForPriority[item.priority.ordinal - 1]
        priorityIndicator.backgroundTintList = ColorStateList.valueOf(color)
        description.text = item.description
    }

}

fun RecyclerView.ViewHolder.asToDoHolder() = this as ToDoHolder

interface OnClickListener {
    fun onItemClick(holder: ToDoHolder)
}
