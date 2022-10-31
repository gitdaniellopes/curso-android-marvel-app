package com.example.marvelapp.framework.di

import br.com.core.usecase.AddFavoriteUseCase
import br.com.core.usecase.AddFavoriteUseCaseImpl
import br.com.core.usecase.GetCharactersUseCase
import br.com.core.usecase.GetCharactersUseCaseImpl
import br.com.core.usecase.GetCharacterCategoriesUseCase
import br.com.core.usecase.GetCharacterCategoriesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetCharactersUseCase(useCase: GetCharactersUseCaseImpl): GetCharactersUseCase

    @Binds
    fun bindGetComicsUseCase(
        useCase: GetCharacterCategoriesUseCaseImpl
    ): GetCharacterCategoriesUseCase

    @Binds
    fun bindAddFavoriteUseCase(
        useCaseImpl: AddFavoriteUseCaseImpl
    ): AddFavoriteUseCase
}