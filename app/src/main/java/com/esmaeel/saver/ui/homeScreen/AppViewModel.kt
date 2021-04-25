package com.esmaeel.saver.ui.homeScreen

import com.esmaeel.saver.base.BaseViewModel
import com.esmaeel.saver.base.ContextProviders
import com.esmaeel.saver.base.ViewState
import com.esmaeel.saver.db.CardItemEntity
import com.esmaeel.saver.db.TodoItemEntity
import kotlinx.coroutines.flow.collect

class AppViewModel(
    private val appRepository: AppRepository,
    contextProviders: ContextProviders
) : BaseViewModel(contextProviders) {


    fun getCards() = launchBlock {
        appRepository
            .getCards()
            .collect {
                setState(HomeScreenState.OnCardsLoaded(HomeResponse(it)))
            }
    }

    fun addCard(cardItemEntity: CardItemEntity) = launchBlock(emitLoading = false) {
        appRepository.addCard(cardItemEntity)
        appRepository.getCards().collect {
            setState(HomeScreenState.OnAddSuccessfully(HomeResponse(it)))
        }
    }

    fun deleteCard(cardItemEntity: CardItemEntity) = launchBlock(emitLoading = false) {
        appRepository.deleteCard(cardItemEntity)
    }


    fun addTodo(todoItemEntity: TodoItemEntity) = launchBlock(emitLoading = false) {
        appRepository
            .addTodo(todoItemEntity = todoItemEntity)
    }

    fun getTodos(cardId: Int) = launchBlock {
        appRepository
            .getTodos(cardId)
            .collect {
//                setState(ViewState())
            }
    }

    /**
     * gets a list of todos where their history_id == todoId
     */
    fun getTodoHistory(cardId: Int, todoParentId: Int) = launchBlock {
      /*  appRepository
            .getTodosHistoryList(cardId = cardId, parentTodoId = todoParentId)
            .collect {
                setState()
            }*/
    }
}