package com.example.marvelapp.framework.di

import br.com.core.usecase.base.AppCoroutinesDispatchers
import br.com.core.usecase.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    // Quando alguem precisar de um CoroutinesDispatchers, eu vou injetar para ele
    // dispatchers: AppCoroutinesDispatchers

    @Binds
    fun bindDispatchers(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}