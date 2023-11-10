package org.rasulov.todoapp.utilities.recyclerview.adapterdelegate

import androidx.recyclerview.widget.DiffUtil


class ItemDiffUtil<I : Any>(
    private val onAreItemsTheSame: (oldItem: I, newItem: I) -> Boolean,
    private val onAreContentsTheSame: (oldItem: I, newItem: I) -> Boolean,
    private val onGetChangePayload: ((oldItem: I, newItem: I) -> Any?)?
) : DiffUtil.ItemCallback<I>() {

    override fun areItemsTheSame(
        oldItem: I, newItem: I
    ): Boolean = onAreItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(
        oldItem: I, newItem: I
    ): Boolean = onAreContentsTheSame(oldItem, newItem)

    override fun getChangePayload(oldItem: I, newItem: I): Any? {
        return onGetChangePayload?.invoke(oldItem, newItem)
            ?: super.getChangePayload(oldItem, newItem)
    }
}