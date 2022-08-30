package org.rasulov.todoapp.sources.room.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.sources.room.api.entities.ToDoDBEntity

@Dao
interface ToDoDao {

    @Query(
        "SELECT * FROM todo_table ORDER BY " +
                "CASE WHEN :order = 0 THEN priority END ASC, " +
                "CASE WHEN :order = 2 THEN priority END DESC"
    )
    fun getAllTasks(order: Int): Flow<List<ToDoDBEntity>>


    @Insert
    suspend fun insertTask(task: ToDoDBEntity)


}