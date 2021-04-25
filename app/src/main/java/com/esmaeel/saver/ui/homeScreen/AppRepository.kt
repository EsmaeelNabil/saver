package com.esmaeel.saver.ui.homeScreen

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esmaeel.saver.db.LocalDatabaseDao
import com.esmaeel.saver.base.BaseRepository
import com.esmaeel.saver.base.ContextProviders
import com.esmaeel.saver.db.CardItemEntity
import com.esmaeel.saver.db.TodoItemEntity
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val localDataSource: LocalDatabaseDao,
    contextProviders: ContextProviders
) : BaseRepository(contextProviders) {

    fun getCards() = localDataSource.getCards()

    suspend fun addCard(cardItemEntity: CardItemEntity) =
        localDataSource.addCard(cardItemEntity = cardItemEntity)


    suspend fun deleteCard(cardItemEntity: CardItemEntity) =
        localDataSource.deleteCard(cardItemEntity = cardItemEntity)


    //-------------------------------------------- TODOS -------------------------------------------------//

    suspend fun addTodo(todoItemEntity: TodoItemEntity) =
        localDataSource.addTodo(todoItemEntity = todoItemEntity)


    fun getTodos(cardId: Int) = localDataSource.getTodos(cardId)

    fun getTodosHistoryList(cardId: Int, parentTodoId: Int) = localDataSource
        .getTodosHistoryList(cardId = cardId, parentTodoId = parentTodoId)

    suspend fun deleteTodo(todoItemEntity: TodoItemEntity) = localDataSource
        .deleteTodo(todoItemEntity = todoItemEntity)

}