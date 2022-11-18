package org.rasulov.utilities.rv_adapter_delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter


open class ItemsAdapter(
    private val mediator: MediatorItemDelegate<Any>
) : ListAdapter<Any, ItemViewHolder<Any>>(mediator.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<Any> {
        return mediator.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<Any>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder<Any>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) holder.bind(getItem(position), payloads)
        else super.onBindViewHolder(holder, position, payloads)
    }

    override fun onViewRecycled(holder: ItemViewHolder<Any>) {
        holder.unBind()
    }

    override fun getItemViewType(position: Int): Int {
        return mediator.getItemViewType(getItem(position))
    }

}

