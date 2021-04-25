package com.esmaeel.saver.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalDatabaseDao {


    /*-------------------------------------- CARDS -----------------------------*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(cardItemEntity: CardItemEntity)

    @Query("SELECT * FROM carditementity")
    fun getCards(): Flow<List<CardItemEntity>>

    @Delete
    suspend fun deleteCard(cardItemEntity: CardItemEntity)

    /*-------------------------------------- TODOS -----------------------------*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todoItemEntity: TodoItemEntity)

    @Delete
    suspend fun deleteTodo(todoItemEntity: TodoItemEntity)


    @Query("SELECT * FROM todoitementity WHERE card_id == :cardId ")
    fun getTodos(cardId: Int): Flow<List<TodoItemEntity>>

    @Query("SELECT * FROM todoitementity WHERE card_id == :cardId AND history_id == :parentTodoId ")
    fun getTodosHistoryList(cardId: Int, parentTodoId: Int): Flow<List<TodoItemEntity>>


    /*------------------------------------- get -----------------------------*/


}