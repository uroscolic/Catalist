package com.rma.catalist.list.api

sealed class CatListUiEvent {
    data class SearchQueryChanged(val query: String) : CatListUiEvent()
    data object ClearSearch : CatListUiEvent()
    data object CloseSearch : CatListUiEvent()
}