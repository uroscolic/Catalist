package com.rma.catalist.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.catalist.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatListViewModel constructor(
    private val repository: Repository = Repository
) : ViewModel() {

    private val _state = MutableStateFlow(CatListState())
    val state = _state.asStateFlow()
    private fun setState (reducer : CatListState.() -> CatListState) = _state.getAndUpdate(reducer)
    init {
        loadCats()
    }
    private fun loadCats() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                withContext(Dispatchers.IO) {
                    val cats = repository.fetchAllCats()
                    setState { copy(cats = cats) }
                }
            } catch (e: Exception) {
                setState { copy(error = e)}
            } finally {
                setState { copy(loading = false) }
            }
        }
    }


}