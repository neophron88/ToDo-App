@file:Suppress("unused")

package org.rasulov.androidx.lifecycle

import androidx.lifecycle.LifecycleOwner

class MutableSingleUseData<T : Any> : SingleUseData<T> {

    constructor() : super()

    constructor(value: T) : super(value)

    var value: T?
        get() = liveData.value?.getValue()
        set(value) {
            liveData.value = Handler(value)
        }


    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        val wrapper = androidx.lifecycle.Observer<Handler<T>> { handler ->
            handler.getValue()?.let {
                observer.invoke(it)
            }
        }
        liveData.observe(lifecycleOwner, wrapper)
    }

    fun postValue(value: T) {
        liveData.postValue(Handler(value))
    }

}
