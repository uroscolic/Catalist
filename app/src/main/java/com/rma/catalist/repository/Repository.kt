package com.rma.catalist.repository

import com.rma.catalist.model.CatInfo


object Repository {

    private var mutableData = SampleData.toMutableList()

    fun allData() : List<CatInfo> = mutableData

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