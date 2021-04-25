package com.esmaeel.saver.di

import androidx.lifecycle.LiveData
import com.esmaeel.saver.db.AppLocalDatabase
import com.esmaeel.saver.db.CardItemEntity
import com.esmaeel.saver.db.LocalDatabaseDao
import com.esmaeel.saver.db.TodoItemEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl constructor(private val appLocalDatabase: AppLocalDatabase) :
    LocalDatabaseDao {

    override suspend fun addCard(cardItemEntity: CardItemEntity) = appLocalDatabase
        .localDatabaseDao()
        .addCard(cardItemEntity = cardItemEntity)

    override suspend fun deleteCard(cardItemEntity: CardItemEntity) {
        appLocalDatabase.localDatabaseDao().deleteCard(cardItemEntity = cardItemEntity)
    }

    override suspend fun addTodo(todoItemEntity: TodoItemEntity) {
        appLocalDatabase.localDatabaseDao()
            .addTodo(todoItemEntity = todoItemEntity)
    }

    override suspend fun deleteTodo(todoItemEntity: TodoItemEntity) {
        appLocalDatabase.localDatabaseDao()
            .deleteTodo(todoItemEntity = todoItemEntity)
    }

    override fun getTodos(cardId: Int): Flow<List<TodoItemEntity>> = appLocalDatabase
        .localDatabaseDao()
        .getTodos(cardId = cardId)


    override fun getTodosHistoryList(cardId: Int, parentTodoId: Int): Flow<List<TodoItemEntity>> =
        appLocalDatabase.localDatabaseDao()
            .getTodosHistoryList(cardId = cardId, parentTodoId = parentTodoId)


    override fun getCards(): Flow<List<CardItemEntity>> {
        return appLocalDatabase
            .localDatabaseDao()
            .getCards()
    }


}