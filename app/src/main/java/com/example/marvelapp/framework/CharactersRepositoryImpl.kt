package com.example.marvelapp.framework

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import br.com.core.data.repository.CharactersRemoteDataSource
import br.com.core.data.repository.CharactersRepository
import br.com.core.domain.model.Character
import br.com.core.domain.model.Comic
import br.com.core.domain.model.Event
import com.example.marvelapp.framework.db.AppDatabase
import com.example.marvelapp.framework.paging.CharactersPagingSource
import com.example.marvelapp.framework.paging.CharactersRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * estamos fazendo nosso repositorio depender de uma abstração (interface) e não uma implementação.
 * com isso, ele não sabe que é o retrofit que esta implementando essa fonte dados remota.
 * Sendo assim, futuramente se formos implementar uma outra fonte de dados remota,nao vai importar,
 * ele só precisa saber das funcões que foram estabelecidas no contrato.
 * */

@OptIn(ExperimentalPagingApi::class)
class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val database: AppDatabase,
) : CharactersRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPagingSource(remoteDataSource, query)
    }

    override fun getCachedCharacters(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Character>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = CharactersRemoteMediator(
                query, database, remoteDataSource
            )
        ) {
            database.characterDao().pagingSource()
        }.flow.map {pagingData ->
            pagingData.map { characterEntity ->
                Character(
                    id = characterEntity.id,
                    name = characterEntity.name,
                    imageUrl = characterEntity.imageUrl
                )
            }
        }
    }

    override suspend fun getComics(characterId: Int): List<Comic> {
        return remoteDataSource.fetchComics(characterId)
    }

    override suspend fun getEvents(characterId: Int): List<Event> {
        return remoteDataSource.fetchEvents(characterId)
    }
}