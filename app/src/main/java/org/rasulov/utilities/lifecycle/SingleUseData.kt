package org.rasulov.utilities.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

abstract class SingleUseData<T : Any> {

    protected val liveData: MutableLiveData<Handler<T>>

    protected constructor() {
        this.liveData = MutableLiveData()
    }

    protected constructor(value: T) {
        this.liveData = MutableLiveData(Handler(value))
    }

    abstract fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>)

}