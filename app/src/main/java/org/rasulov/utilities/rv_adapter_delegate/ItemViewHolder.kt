package org.rasulov.utilities.rv_adapter_delegate

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ItemViewHolder<I : Any>(
    view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: I?)

    open fun bind(item: I?, payloads: MutableList<Any>) {}

}