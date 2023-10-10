package org.rasulov.todoapp.utilities.lifecycle.observer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

abstract class SingleUseData<T : Any> {


    protected abstract val liveData: LiveData<Handler<T>>

    open val value: T?
        get() = liveData.value?.getVal()

    abstract fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>)

}