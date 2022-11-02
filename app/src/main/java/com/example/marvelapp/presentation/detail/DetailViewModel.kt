package com.example.marvelapp.presentation.detail

import androidx.lifecycle.ViewModel
import br.com.core.usecase.AddFavoriteUseCase
import br.com.core.usecase.CheckFavoriteUseCase
import br.com.core.usecase.GetCharacterCategoriesUseCase
import br.com.core.usecase.RemoveFavoriteUseCase
import br.com.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase,
    checkFavoriteUseCase: CheckFavoriteUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    removeFavoriteUseCase: RemoveFavoriteUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    val categories = UiActionStateLiveData(
        coroutineContext = coroutinesDispatchers.main(),
        getCharacterCategoriesUseCase = getCharacterCategoriesUseCase
    )

    val favorite = FavoriteUiActionStateLiveData(
        coroutineContext = coroutinesDispatchers.main(),
        addFavoriteUseCase = addFavoriteUseCase,
        removeFavoriteUseCase = removeFavoriteUseCase,
        checkFavoriteUseCase = checkFavoriteUseCase
    )
}