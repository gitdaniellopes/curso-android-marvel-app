package com.example.marvelapp.presentation.sort

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import br.com.core.usecase.GetCharactersSortingUseCase
import br.com.core.usecase.SaveCharacterSortingUseCase
import br.com.core.usecase.base.AppCoroutinesDispatchers
import com.example.marvelapp.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SortViewModel @Inject constructor(
    private val getCharactersSortingUseCase: GetCharactersSortingUseCase,
    private val saveCharacterSortingUseCase: SaveCharacterSortingUseCase,
    private val coroutinesDispatchers: AppCoroutinesDispatchers
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(coroutinesDispatchers.main()) {
            when (action) {
                Action.GetStorageSorting -> {
                    getCharactersSortingUseCase.invoke().collectLatest { sortingPair ->
                        emit(UiState.SortingResult(sortingPair))
                    }
                }
                is Action.ApplySorting -> {
                    val orderBy = action.orderBy
                    val order = action.order

                    saveCharacterSortingUseCase.invoke(
                        params = SaveCharacterSortingUseCase.Params(
                            orderBy to order
                        )
                    ).watchStatus(
                        loading = {
                            emit(UiState.ApplyState.Loading)
                        },
                        success = {
                            emit(UiState.ApplyState.Success)
                        },
                        error = {
                            emit(UiState.ApplyState.Error)
                        }
                    )
                }
            }
        }
    }

    init {
        action.value = Action.GetStorageSorting
    }

    fun applySorting(orderBy: String, order: String) {
        action.value = Action.ApplySorting(orderBy, order)
    }


    sealed class UiState {
        data class SortingResult(val storageSorting: Pair<String, String>) : UiState()

        sealed class ApplyState : UiState() {
            object Loading : ApplyState()
            object Success : ApplyState()
            object Error : ApplyState()
        }
    }

    sealed class Action {
        object GetStorageSorting : Action()
        data class ApplySorting(val orderBy: String, val order: String) : Action()
    }
}