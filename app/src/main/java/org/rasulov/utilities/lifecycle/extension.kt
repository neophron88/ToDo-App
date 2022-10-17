package org.rasulov.utilities.lifecycle

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*


fun LifecycleOwner.postDelayed(delayMillis: Long = 0, run: () -> Unit) {
    val handler = Handler(Looper.getMainLooper())

    class Link {
        lateinit var runnable: Runnable
        lateinit var observer: LifecycleObserver
    }

    val link = Link()

    link.runnable = Runnable {
        run.invoke()
        this.lifecycle.removeObserver(link.observer)
    }

    link.observer = object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            handler.removeCallbacks(link.runnable)
        }
    }

    this.lifecycle.addObserver(link.observer)
    handler.postDelayed(link.runnable, delayMillis)
}