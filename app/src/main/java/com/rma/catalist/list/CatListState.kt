package com.rma.catalist.list

import com.rma.catalist.list.api.model.CatListUiModel

data class CatListState(
    val loading : Boolean = false,
    val cats: List<CatListUiModel> = emptyList(),
    val error: Throwable? = null
)
