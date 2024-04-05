package com.rma.catalist.list

import com.rma.catalist.domain.CatInfo

data class CatListState(
    val loading : Boolean = false,
    val cats: List<CatInfo> = emptyList(),
    val error: Throwable? = null
)
