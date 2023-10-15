package org.rasulov.todoapp.data.sources.database.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
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
    fun getAllTasks(searchBy: String, priority: Int): Flowable<List<ToDoDBEntity>>


    @Insert
    fun insertTask(task: ToDoDBEntity): Completable

    @Delete(entity = ToDoDBEntity::class)
    fun deleteTask(task: ToDoIDTuple): Completable

    @Update
    fun updateTask(task: ToDoDBEntity): Completable

    @Query("DELETE FROM todo_table")
    fun deleteAll(): Completable

}