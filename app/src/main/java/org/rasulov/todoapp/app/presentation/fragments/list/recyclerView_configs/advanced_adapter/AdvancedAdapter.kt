package org.rasulov.todoapp.app.presentation.fragments.list.recyclerView_configs.advanced_adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


abstract class AdvancedAdapter<T : Any, VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<VH>() {

    private var recyclerView: RecyclerView? = null
    private var itemTouchHelper: ItemTouchHelper? = null
    private var itemCallback: DiffUtil.ItemCallback<T>? = null

    private var data = emptyList<T>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        itemTouchHelper?.attachToRecyclerView(recyclerView)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun submitData(newData: List<T>) {
        val callback = itemCallback

        if (callback != null) {
            val diffUtil = DiffUtilCallback(
                newList = newData,
                oldList = data,
                itemCallback = callback
            )
            val diffResult = DiffUtil.calculateDiff(diffUtil)
            this.data = newData
            diffResult.dispatchUpdatesTo(this)
        } else {
            this.data = newData
            notifyDataSetChanged()
        }

    }

    fun getItem(position: Int): T {
        return data[position]
    }

    override fun getItemCount() = data.size


    fun setSwipeConfig(
        toRight: Boolean = true,
        toLeft: Boolean = true,
        onSwipeListener: OnSwipeListener<T>
    ) {
        var direction = if (toRight) ItemTouchHelper.RIGHT else 0
        direction = if (toLeft) ItemTouchHelper.LEFT or direction else direction
        val itemTouchHelper = ItemTouchHelper(SwipeItemCallback(this, onSwipeListener, direction))
        recyclerView?.let { itemTouchHelper.attachToRecyclerView(it) }
        this.itemTouchHelper = itemTouchHelper
    }

    fun setDiffUtilItemCallBack(callback: DiffUtil.ItemCallback<T>) {
        itemCallback = callback
    }
}
