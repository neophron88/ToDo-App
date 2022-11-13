package org.rasulov.utilities.rv_adapter_delegate

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter


class DelegationAdapter<I : Any>(
    private val mediator: MediatorAdapterDelegate<I>
) : ListAdapter<I, ItemViewHolder<I>>(mediator.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<I> {
        return mediator.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<I>, position: Int) {
        mediator.onBindViewHolder(holder, getItem(position))
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder<I>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) holder.bind(getItem(position), payloads)
        else super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return mediator.getItemViewType(getItem(position))
    }

}

