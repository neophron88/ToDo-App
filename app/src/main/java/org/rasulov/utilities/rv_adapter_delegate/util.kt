package org.rasulov.utilities.rv_adapter_delegate


fun ItemsAdapter(vararg args: ItemDelegate<out Any>): ItemsAdapter {
    val builder = MediatorItemDelegate.Builder()
    args.forEach {
        builder.addItemDelegate(it)
    }
    return ItemsAdapter(builder.build())
}