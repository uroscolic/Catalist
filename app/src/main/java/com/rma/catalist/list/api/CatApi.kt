package com.rma.catalist.list.api

import com.rma.catalist.domain.CatInfo
import retrofit2.http.GET

interface CatApi {
    @GET("breeds")
    suspend fun getAllCats(): List<CatInfo>

}