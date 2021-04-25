package com.esmaeel.saver.ui.homeScreen

import com.esmaeel.saver.base.ViewState

sealed class HomeScreenState : ViewState() {

    data class OnCardsLoaded(val response: HomeResponse) :
        Loaded<HomeResponse>(response)

    data class OnAddSuccessfully(val response: HomeResponse) :
        Loaded<HomeResponse>(response)

}