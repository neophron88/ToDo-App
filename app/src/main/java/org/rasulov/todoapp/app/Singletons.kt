package org.rasulov.todoapp.app

import android.content.Context
import org.rasulov.todoapp.app.data.ToDoRepository
import org.rasulov.todoapp.sources.SourceProviderHandler

object Singletons {

    private lateinit var appContext: Context

    fun init(appC: Context) {
        appContext = appC
    }

    private val roomSourceProvider by lazy {
        SourceProviderHandler.getPersistenceSourceProvider(appContext)
    }

    val toDoRepository by lazy {
        ToDoRepository(roomSourceProvider.getToDoSource())
    }

}