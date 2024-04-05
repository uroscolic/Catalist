package com.rma.catalist.repository

import com.rma.catalist.domain.CatInfo
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


object Repository {

    private var mutableData = SampleData.toMutableList()

    fun allData() : List<CatInfo> = mutableData

    suspend fun fetchAllCats() : List<CatInfo> {
        delay(2.seconds)
        return mutableData
    }
    suspend fun fetchCatById(id: String) : CatInfo? {
        delay(2.seconds)
        return getById(id)
    }

    fun getById(id: String) : CatInfo? {
        return mutableData.find { it.id == id }
    }
    fun search(search: String) : List<CatInfo> {
        if(search.isEmpty())
            return mutableData
        return mutableData.filter { it.name.contains(search, ignoreCase = true)
            || it.alternativeName.contains(search, ignoreCase = true) }
    }
}