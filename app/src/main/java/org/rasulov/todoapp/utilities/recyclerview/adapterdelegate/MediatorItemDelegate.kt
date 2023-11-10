package org.rasulov.todoapp.utilities.recyclerview.adapterdelegate

import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class MediatorItemDelegate<I : Any> private constructor(
    delegates: List<ItemDelegate<out I>>
) {

    private val delegateByViewType = SparseArray<ItemDelegate<I>>()
    private val viewTypeByClass = HashMap<KClass<out I>, Int>()
    private val delegateByClass = HashMap<KClass<out I>, ItemDelegate<I>>()


    init {
        val castedDelegates = delegates as List<ItemDelegate<I>>
        decomposeDelegates(castedDelegates)
    }

    private fun decomposeDelegates(delegates: List<ItemDelegate<I>>) =
        delegates.forEach { itemDelegate ->
            delegateByViewType.put(itemDelegate.layout, itemDelegate)
            val itemClass = itemDelegate.itemClass
            viewTypeByClass[itemClass] = itemDelegate.layout
            delegateByClass[itemClass] = itemDelegate

        }

    fun createViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<I> {
        val inflater = LayoutInflater.from(parent.context)
        val itemDelegate = delegateByViewType[viewType]
        val view = inflater.inflate(itemDelegate.layout, parent, false)
        return itemDelegate.itemViewHolder(view)
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
            if (oldClass != newItem::class) return null
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