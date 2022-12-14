package org.rasulov.todoapp.data.sources.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.rasulov.todoapp.data.sources.database.room.entities.ToDoDBEntity

@Database(version = 1, entities = [ToDoDBEntity::class])
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun getToDoDao(): ToDoDao


    companion object {
        @Volatile
        private var database: ToDoDatabase? = null

        fun getDatabase(appContext: Context): ToDoDatabase {
            val temp1 = database
            if (temp1 != null) return temp1

            synchronized(this) {
                val temp2 = database
                if (temp2 != null) return temp2
                val temp3 = Room.databaseBuilder(
                    appContext,
                    ToDoDatabase::class.java,
                    "todo"
                )
                    .createFromAsset("todo.db")
                    .build()
                database = temp3
                return temp3
            }
        }
    }
}