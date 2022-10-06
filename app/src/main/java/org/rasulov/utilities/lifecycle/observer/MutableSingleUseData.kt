@file:Suppress("unused")

package org.rasulov.utilities.lifecycle.observer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

class MutableSingleUseData<T : Any> : SingleUseData<T> {

    override val liveData: MutableLiveData<Handler<T>>

    constructor() : super() {
        liveData = MutableLiveData()
    }

    constructor(value: T) : super() {
        liveData = MutableLiveData(Handler(value))
    }

    var value: T?
        get() = liveData.value?.getVal()
        set(value) {
            liveData.value = Handler(value)
        }


    fun postValue(value: T) {
        liveData.postValue(Handler(value))
    }


    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        val wrapper = androidx.lifecycle.Observer<Handler<T>> { handler ->
            handler.getVal()?.let {
                observer.invoke(it)
            }
        }
        liveData.observe(lifecycleOwner, wrapper)
    }
}
