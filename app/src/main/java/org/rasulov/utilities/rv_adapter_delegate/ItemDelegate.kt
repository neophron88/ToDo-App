package org.rasulov.utilities.rv_adapter_delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import kotlin.reflect.KClass

abstract class ItemDelegate<I : Any>(
    val itemClass: KClass<I>
) {

    abstract fun createViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<I>

    abstract fun areItemsTheSame(oldItem: I, newItem: I): Boolean

    abstract fun areContentsTheSame(oldItem: I, newItem: I): Boolean

}