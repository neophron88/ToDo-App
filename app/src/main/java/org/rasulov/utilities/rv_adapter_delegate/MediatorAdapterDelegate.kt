package org.rasulov.utilities.rv_adapter_delegate

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import kotlin.reflect.KClass

class MediatorAdapterDelegate<I : Any>(
    delegates: List<ItemDelegate<out I>>
) {

    private val delegateByViewType = SparseArray<ItemDelegate<I>>()
    private val viewTypeByClass = HashMap<KClass<out I>, Int>()
    private val delegateByClass = HashMap<KClass<out I>, ItemDelegate<I>>()


    init {
        val castedDelegates = delegates as List<ItemDelegate<I>>
        configureViewType(castedDelegates)
    }

    private fun configureViewType(
        delegates: List<ItemDelegate<I>>
    ) = delegates.forEachIndexed { indexAsViewType, itemDelegate ->

        delegateByViewType.put(indexAsViewType, itemDelegate)

        val kClass = itemDelegate.itemClass
        viewTypeByClass[kClass] = indexAsViewType
        delegateByClass[kClass] = itemDelegate

    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<I> {
        val inflater = LayoutInflater.from(parent.context)
        return delegateByViewType[viewType].createViewHolder(inflater, parent)

    }

    fun onBindViewHolder(holder: ItemViewHolder<I>, item: I?) {
        holder.bind(item)
    }

    fun getItemViewType(item: I): Int {
        val kClass = item::class
        return viewTypeByClass[kClass]
            ?: throw error(kClass)
    }

    val diffUtil = object : DiffUtil.ItemCallback<I>() {
        override fun areItemsTheSame(oldItem: I, newItem: I): Boolean {
            val oldClass = oldItem::class
            if (oldClass != newItem::class) return false
            return delegateByClass[oldClass]?.areItemsTheSame(oldItem, newItem)
                ?: throw error(oldClass)
        }

        override fun areContentsTheSame(oldItem: I, newItem: I): Boolean {
            val oldClass = oldItem::class
            if (oldClass != newItem::class) return false
            return delegateByClass[oldClass]?.areContentsTheSame(oldItem, newItem)
                ?: throw error(oldClass)
        }

        override fun getChangePayload(oldItem: I, newItem: I): Any? {
            val oldClass = oldItem::class
            if (oldClass != newItem::class) return false
            val delegate = delegateByClass[oldClass] ?: throw error(oldClass)
            return delegate.getChangePayload(oldItem, newItem)
        }
    }

    fun error(klass: KClass<out I>): IllegalStateException {
        return IllegalStateException(
            "Can not find ItemDelegate for ${klass.simpleName} class "
        )
    }


    class Builder<I : Any> {
        private val delegates = mutableListOf<ItemDelegate<out I>>()

        fun addItemDelegate(item: ItemDelegate<out I>): Builder<I> {
            delegates.add(item)
            return this
        }

        fun build() = MediatorAdapterDelegate(delegates)
    }

}