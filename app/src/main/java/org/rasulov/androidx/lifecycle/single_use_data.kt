@file:Suppress("unused")

package org.rasulov.androidx.lifecycle


typealias Observer<T> = (T) -> Unit

fun <T : Any> MutableSingleUseData<T>.toSingleUseData(): SingleUseData<T> {
    return this
}

