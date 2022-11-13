package org.rasulov.todoapp.presentation.fragments.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.rasulov.utilities.rv_adapter_delegate.ItemViewHolder
import org.rasulov.utilities.rv_adapter_delegate.MediatorAdapterDelegate


class DelegationAdapter<I : Any>(
    private val mediator: MediatorAdapterDelegate<I>
) : ListAdapter<I, ItemViewHolder<I>>(mediator.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<I> {
        return mediator.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<I>, position: Int) {
        mediator.onBindViewHolder(holder, getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return mediator.getItemViewType(getItem(position))
    }

}

