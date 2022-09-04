package org.rasulov.androidx.lifecycle

 class Handler<T>(
    value: T?
) {
    private var actualData: T? = value

    fun getValue(): T? {
        val temp = actualData
        actualData = null
        return temp
    }
}