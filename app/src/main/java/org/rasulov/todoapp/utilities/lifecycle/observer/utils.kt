@file:Suppress("unused")

package org.rasulov.todoapp.utilities.lifecycle.observer


typealias Observer<T> = (T) -> Unit

fun <T : Any> MutableSingleUseData<T>.toSingleUseData(): SingleUseData<T> = this

