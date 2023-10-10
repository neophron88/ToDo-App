package org.rasulov.todoapp.utilities.rv_adapter_delegate

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import kotlin.reflect.KClass

class MediatorItemDelegate<I : Any> private constructor(
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
    ) = delegates.forEach { itemDelegate ->

        delegateByViewType.put(itemDelegate.layout, itemDelegate)

        val kClass = itemDelegate.itemClass
        viewTypeByClass[kClass] = itemDelegate.layout
        delegateByClass[kClass] = itemDelegate

    }

    fun createViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<I> {
        val inflater = LayoutInflater.from(parent.context)
        val itemDelegate = delegateByViewType[viewType]
        val view = inflater.inflate(itemDelegate.layout, parent, false)
        return itemDelegate.itemViewHolderProducer(view)

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
            val diffUtil = delegateByClass[oldClass]?.diffUtil ?: throw error(oldClass)
            return diffUtil.areItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: I, newItem: I): Boolean {
            val oldClass = oldItem::class
            if (oldClass != newItem::class) return false
            val diffUtil = delegateByClass[oldClass]?.diffUtil ?: throw error(oldClass)
            return diffUtil.areContentsTheSame(oldItem, newItem)
        }

        override fun getChangePayload(oldItem: I, newItem: I): Any? {
            val oldClass = oldItem::class
            if (oldClass != newItem::class) return false
            val diffUtil = delegateByClass[oldClass]?.diffUtil ?: throw error(oldClass)
            return diffUtil.getChangePayload(oldItem, newItem)
        }
    }

    fun error(klass: KClass<out I>) = IllegalStateException(
        "Can not find ItemDelegate for ${klass.simpleName} class"
    )

    class Builder {
        private val delegates = mutableListOf<ItemDelegate<out Any>>()

        fun addItemDelegate(item: ItemDelegate<out Any>): Builder {
            delegates.add(item)
            return this
        }

        fun build() = MediatorItemDelegate(delegates)
    }

}