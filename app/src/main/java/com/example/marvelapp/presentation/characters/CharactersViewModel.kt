package com.example.marvelapp.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.core.domain.model.Character
import br.com.core.usecase.GetCharactersUseCase
import br.com.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * distinctUntilChanged()
 * só notifica o liveData se os dados que estão no state forem diferentes.
 * */

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    var currentSearchQuery = ""

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action
        .switchMap { action ->
            when (action) {
                is Action.Search, Action.Sort -> {
                    getCharactersUseCase(
                        params =
                        GetCharactersUseCase.GetCharactersParams(
                            query = currentSearchQuery,
                            pagingConfig = getPagingConfig()
                        )
                    ).cachedIn(viewModelScope).map { pagingData ->
                        UiState.SearchResult(pagingData)
                    }.asLiveData(coroutinesDispatchers.main())
                }
            }
        }

    fun charactersPagingData(query: String): Flow<PagingData<Character>> {
        return getCharactersUseCase(
            params =
            GetCharactersUseCase.GetCharactersParams(
                query = query,
                pagingConfig = getPagingConfig()
            )
        ).cachedIn(viewModelScope)
    }

    private fun getPagingConfig() = PagingConfig(
        pageSize = 20
    )

    fun searchCharacters() {
        action.value = Action.Search
    }

    fun applySort() {
        action.value = Action.Sort
    }

    fun closeSearch() {
        if (currentSearchQuery.isNotEmpty()) {
            currentSearchQuery = ""
        }
    }

    sealed class UiState {
        data class SearchResult(val data: PagingData<Character>) : UiState()
    }

    sealed class Action {
        object Search : Action()
        object Sort : Action()
    }
}