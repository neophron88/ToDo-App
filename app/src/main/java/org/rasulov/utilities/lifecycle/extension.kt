package org.rasulov.utilities.lifecycle

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner


fun LifecycleOwner.postDelayed(delayMillis: Long = 0, run: () -> Unit) {
    val handler = Handler(Looper.getMainLooper())

    class Link {
        lateinit var runnable: Runnable
        lateinit var observer: LifecycleEventObserver
    }

    val link = Link()

    link.runnable = Runnable {
        run.invoke()
        this.lifecycle.removeObserver(link.observer)
    }

    link.observer = LifecycleEventObserver { _, event ->
        if (event.targetState == Lifecycle.State.DESTROYED) {
            handler.removeCallbacks(link.runnable)
        }
    }

    this.lifecycle.addObserver(link.observer)
    handler.postDelayed(link.runnable, delayMillis)
}