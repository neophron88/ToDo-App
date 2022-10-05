package org.rasulov.utilities.recyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

typealias OnSwipeListener = (viewHolder: RecyclerView.ViewHolder, direction: Int) -> Unit

fun RecyclerView.setSwipeItem(
    toRight: Boolean = true,
    toLeft: Boolean = true,
    onSwipeListener: OnSwipeListener
) {
    var direction = if (toRight) ItemTouchHelper.RIGHT else 0

    direction = if (toLeft) ItemTouchHelper.LEFT or direction else direction

    val itemTouchHelper = ItemTouchHelper(SwipeItemCallback(onSwipeListener, direction))

    itemTouchHelper.attachToRecyclerView(this)
}


class SwipeItemCallback(
    private val onSwipeListener: OnSwipeListener,
    direction: Int
) : ItemTouchHelper.SimpleCallback(0, direction) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipeListener(viewHolder, direction)
    }
}