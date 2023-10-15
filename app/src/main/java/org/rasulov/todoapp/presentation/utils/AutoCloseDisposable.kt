package org.rasulov.todoapp.presentation.utils

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.Closeable

class AutoCloseCompositeDisposable : Closeable {

    val disposables = CompositeDisposable()

    operator fun plus(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun close() {
        disposables.dispose()
    }
}

@Suppress("NOTHING_TO_INLINE", "FunctionName")
inline fun ViewModel.AutoCloseDisposable() =
    AutoCloseCompositeDisposable()
        .also { addCloseable(it) }


