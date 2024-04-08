package com.rma.catalist.repository

import com.rma.catalist.domain.CatInfo
import com.rma.catalist.list.api.CatApi
import com.rma.catalist.list.api.model.CatListUiModel
import com.rma.catalist.networking.retrofit
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


object Repository {

    private var mutableData = SampleData.toMutableList()
    private val catApi : CatApi = retrofit.create(CatApi::class.java)

    fun allData() : List<CatInfo> = mutableData

    suspend fun fetchAllCats() : List<CatInfo> {
        val allCats = catApi.getAllCats()
        mutableData = allCats.toMutableList()
        return allCats
    }
    suspend fun fetchCatById(id: String) : CatInfo? {
        delay(300)
        return getById(id)
    }

    fun getById(id: String) : CatInfo? {
        return mutableData.find { it.id == id }
    }
    fun search(search: String) : List<CatInfo> {
        if(search.isEmpty())
            return mutableData
        return mutableData.filter { it.name.contains(search, ignoreCase = true)
            || it.alt_names.contains(search, ignoreCase = true) }
    }

}