package com.example.marvelapp.framework.di

import br.com.core.data.repository.FavoritesLocalDataSource
import br.com.core.data.repository.FavoritesRepository
import com.example.marvelapp.framework.FavoritesRepositoryImpl
import com.example.marvelapp.framework.local.RoomFavoritesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesRepositoryModule {

    @Binds
    fun bindFavoritesRepository(repositoryImpl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    fun bindRemoteDataSource(dataSource: RoomFavoritesDataSource): FavoritesLocalDataSource
}