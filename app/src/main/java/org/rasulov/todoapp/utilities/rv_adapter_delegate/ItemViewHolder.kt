package org.rasulov.todoapp.utilities.rv_adapter_delegate

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ItemViewHolder<I : Any>(
    view: View
) : RecyclerView.ViewHolder(view) {

    lateinit var item: I

    internal fun bind(item: I) {
        this.item = item
        onBind(item)
    }

    internal fun bind(item: I, payloads: MutableList<Any>) {
        this.item = item
        onBind(item, payloads)
    }


    abstract fun onBind(item: I)

    open fun onBind(item: I, payloads: MutableList<Any>) {}

    open fun unBind() {}

}