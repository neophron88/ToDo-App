package org.rasulov.todoapp.app.presentation.fragments.list.recyclerView_configs.advanced_adapter

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T : Any>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val itemCallback: DiffUtil.ItemCallback<T>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return itemCallback.areItemsTheSame(
            oldList[oldItemPosition],
            newList[newItemPosition]
        )
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return itemCallback.areContentsTheSame(
            oldList[oldItemPosition],
            newList[newItemPosition]
        )
    }
}