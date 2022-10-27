package org.rasulov.utilities.lifecycle.observer

class Handler<T>(private var value: T?) {

    fun getVal(): T? {
        return value?.also { value = null }
    }
}