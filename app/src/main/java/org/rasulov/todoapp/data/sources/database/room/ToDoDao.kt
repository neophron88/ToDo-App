package org.rasulov.todoapp.data.sources.database.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.rasulov.todoapp.data.sources.database.room.entities.ToDoDBEntity
import org.rasulov.todoapp.data.sources.database.room.entities.ToDoIDTuple


@Dao
interface ToDoDao {

    @Query(
        "SELECT * FROM todo_table " +
                "WHERE :searchBy = '' OR title LIKE '%' || :searchBy || '%' " +
                "ORDER BY " +
                "CASE WHEN :priority = 0 THEN id END ," +
                "CASE WHEN :priority = 1 THEN priority END ASC, " +
                "CASE WHEN :priority = 3 THEN priority END DESC"
    )
    fun getAllTasks(searchBy: String, priority: Int): Flow<List<ToDoDBEntity>>


    @Insert
    suspend fun insertTask(task: ToDoDBEntity)

    @Delete(entity = ToDoDBEntity::class)
    suspend fun deleteTask(task: ToDoIDTuple)

    @Update
    suspend fun updateTask(task: ToDoDBEntity)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

}