package org.rasulov.utilities.lifecycle.observer

class Handler<T>(private var value: T?) {

    fun getVal(): T? {
        val temp = value
        value = null
        return temp
    }
}