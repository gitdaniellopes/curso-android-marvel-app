package com.example.marvelapp.framework

import br.com.core.data.repository.FavoritesLocalDataSource
import br.com.core.data.repository.FavoritesRepository
import br.com.core.domain.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource
) : FavoritesRepository {
    override fun getAll(): Flow<List<Character>> =
        favoritesLocalDataSource.getAll()

    override suspend fun isFavorite(characterId: Int): Boolean =
        favoritesLocalDataSource.isFavorite(characterId)

    override suspend fun saveFavorite(character: Character) =
        favoritesLocalDataSource.save(character)

    override suspend fun deleteFavorite(character: Character) =
        favoritesLocalDataSource.delete(character)
}