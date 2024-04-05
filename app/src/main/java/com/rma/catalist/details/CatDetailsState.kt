package com.rma.catalist.details

import com.rma.catalist.domain.CatInfo

data class CatDetailsState(
    val loading : Boolean = false,
    val cat : CatInfo? = null,
    val catId : String,
    val error : Throwable? = null
)
