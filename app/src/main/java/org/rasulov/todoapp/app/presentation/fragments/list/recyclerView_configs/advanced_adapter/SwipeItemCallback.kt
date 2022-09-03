package org.rasulov.todoapp.app.presentation.fragments.list.recyclerView_configs.advanced_adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeItemCallback<T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: AdvancedAdapter<T, VH>,
    private val onSwipeListener: OnSwipeListener<T>,
    direction: Int
) : ItemTouchHelper.SimpleCallback(0, direction) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipeListener(adapter.getItem(viewHolder.adapterPosition), direction)
    }
}