package com.example.marvelapp.framework

import androidx.paging.PagingSource
import br.com.core.data.repository.CharactersRemoteDataSource
import br.com.core.data.repository.CharactersRepository
import br.com.core.domain.model.Character
import br.com.core.domain.model.Comic
import br.com.core.domain.model.Event
import com.example.marvelapp.framework.paging.CharactersPagingSource
import javax.inject.Inject

/**
 * estamos fazendo nosso repositorio depender de uma abstração (interface) e não uma implementação.
 * com isso, ele não sabe que é o retrofit que esta implementando essa fonte dados remota.
 * Sendo assim, futuramente se formos implementar uma outra fonte de dados remota,nao vai importar,
 * ele só precisa saber das funcões que foram estabelecidas no contrato.
 * */

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource
) : CharactersRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPagingSource(remoteDataSource, query)
    }

    override suspend fun getComics(characterId: Int): List<Comic> {
        return remoteDataSource.fetchComics(characterId)
    }

    override suspend fun getEvents(characterId: Int): List<Event> {
        return remoteDataSource.fetchEvents(characterId)
    }
}