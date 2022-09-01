package org.rasulov.todoapp.sources.persistence_source.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.app.domain.entities.Priority
import org.rasulov.todoapp.sources.persistence_source.room.entities.ToDoDBEntity

@Dao
interface ToDoDao {

    @Query(
        "SELECT * FROM todo_table " +
                "WHERE :searchBy = '' OR title LIKE :searchBy || '%' " +
                "ORDER BY CASE WHEN :priority = 0 THEN id END ASC, " +
                "CASE WHEN :priority = 1 THEN priority END ASC, " +
                "CASE WHEN :priority = 3 THEN priority END DESC"
    )
    fun getAllTasks(searchBy: String, priority: Int): Flow<List<ToDoDBEntity>>


    @Insert
    suspend fun insertTask(task: ToDoDBEntity)



}