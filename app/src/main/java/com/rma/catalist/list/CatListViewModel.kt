package com.rma.catalist.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.catalist.domain.CatInfo
import com.rma.catalist.list.api.model.CatListUiModel
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
                    val cats = repository.fetchAllCats().map { it.asCatListUiModel() }
                    setState { copy(cats = cats) }
                }
            } catch (e: Exception) {
                setState { copy(error = e)}
            } finally {
                setState { copy(loading = false) }
            }
        }
    }
    private fun CatInfo.asCatListUiModel() = CatListUiModel(
        id = this.id,
        name = this.name,
        alt_names = this.alt_names,
        description = this.description,
        temperament = this.temperament
    )

}