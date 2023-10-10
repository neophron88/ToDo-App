package org.rasulov.todoapp.utilities.rv_adapter_delegate

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

typealias ItemsTheSamePointer<I, R> = (item: I) -> R

class ItemDiffUtil<I : Any, R>(
    val itemsTheSamePointer: ItemsTheSamePointer<I, R>
) : DiffUtil.ItemCallback<I>() {

    override fun areItemsTheSame(
        oldItem: I, newItem: I
    ): Boolean = itemsTheSamePointer(oldItem) == itemsTheSamePointer(newItem)

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: I, newItem: I
    ): Boolean = oldItem == newItem
}