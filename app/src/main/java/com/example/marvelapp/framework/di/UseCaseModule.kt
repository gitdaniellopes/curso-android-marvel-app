package com.example.marvelapp.framework.di

import br.com.core.usecase.AddFavoriteUseCase
import br.com.core.usecase.AddFavoriteUseCaseImpl
import br.com.core.usecase.CheckFavoriteUseCase
import br.com.core.usecase.CheckFavoriteUseCaseImpl
import br.com.core.usecase.GetCharacterCategoriesUseCase
import br.com.core.usecase.GetCharacterCategoriesUseCaseImpl
import br.com.core.usecase.GetCharactersUseCase
import br.com.core.usecase.GetCharactersUseCaseImpl
import br.com.core.usecase.GetFavoritesUseCase
import br.com.core.usecase.GetFavoritesUseCaseImpl
import br.com.core.usecase.RemoveFavoriteUseCase
import br.com.core.usecase.RemoveFavoriteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetCharactersUseCase(
        useCase: GetCharactersUseCaseImpl
    ): GetCharactersUseCase

    @Binds
    fun bindGetComicsUseCase(
        useCase: GetCharacterCategoriesUseCaseImpl
    ): GetCharacterCategoriesUseCase

    @Binds
    fun bindAddFavoriteUseCase(
        useCaseImpl: AddFavoriteUseCaseImpl
    ): AddFavoriteUseCase

    @Binds
    fun bindCheckFavoriteUseCase(
        useCaseImpl: CheckFavoriteUseCaseImpl
    ): CheckFavoriteUseCase

    @Binds
    fun bindRemoveFavoriteUseCase(
        useCaseImpl: RemoveFavoriteUseCaseImpl
    ): RemoveFavoriteUseCase

    @Binds
    fun bindGetFavoritesUseCase(
        useCase: GetFavoritesUseCaseImpl
    ): GetFavoritesUseCase
}