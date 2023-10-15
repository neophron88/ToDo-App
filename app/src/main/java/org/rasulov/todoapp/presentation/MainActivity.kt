package org.rasulov.todoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.rasulov.todoapp.R
import org.rasulov.todoapp.databinding.ActivityMainBinding
import org.rasulov.todoapp.presentation.utils.navControllers
import org.rasulov.todoapp.utilities.activity.viewBindings
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val binding: ActivityMainBinding by viewBindings()

    private val navController by navControllers(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActionBarWithNavController(navController)
    }

    private fun dataSource() = Flowable.create<Int>({ emitter ->
        for (i in 0..1000) {
            Thread.sleep(1)
            Log.d("it0088", "dataSource: ${Thread.currentThread().name} $i")
            emitter.onNext(i)
        }
        emitter.onComplete()
    }, BackpressureStrategy.DROP)

    fun rx2() {
        val observable = Observable.switchOnNext(
            Observable.just(Observable.just(""))
        )

        val disposable = Observable.just("a", "b", "c")
            .switchMap { str ->
                Log.d("it0088", "flatMap: ${Thread.currentThread().name} == single")
                Thread.sleep(Random.nextLong(5000))
                Observable.create<String> { emitter ->
                    for (i in 1..5) {
                        Thread.sleep(2000)
                        emitter.onNext("$str $i")
                    }
                    emitter.onComplete()
                }.subscribeOn(Schedulers.newThread())
            }
//            .map { Log.d("it0088", "map: ${Thread.currentThread().name} == newThread") }
            .subscribeOn(Schedulers.single())
            .subscribe {
//                Log.d("it0088", "subscribe: ${Thread.currentThread().name} == newThread")
                Log.d("it0088", "subscribe: ${it}")
            }
    }


    fun rx1() {
        val subscribe = Observable.just("Hey")
            .map {
                Log.d("it0088", "map: ${Thread.currentThread().name} = computation")
            }
            .subscribeOn(Schedulers.computation())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                Log.d("it0088", "doOnSubscribe: ${Thread.currentThread().name} == new")
            }
            .subscribeOn(Schedulers.newThread())
            .doOnSubscribe {
                Log.d("it0088", "doOnSubscribe 12: ${Thread.currentThread().name} io cached")
            }
            .flatMap {
                Log.d("it0088", "flatMap: ${Thread.currentThread().name} == computation")

                Observable.just(1)
                    .map {
                        Log.d("it0088", "map timer: ${Thread.currentThread().name} single")
                    }.subscribeOn(Schedulers.single())
            }
            .subscribeOn(Schedulers.io())
            .subscribe {
                Log.d("it0088", "subscribe: ${Thread.currentThread().name} == single")
            }
    }
}