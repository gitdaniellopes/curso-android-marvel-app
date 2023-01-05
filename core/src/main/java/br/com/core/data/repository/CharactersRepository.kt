package br.com.core.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import br.com.core.domain.model.Character
import br.com.core.domain.model.Comic
import br.com.core.domain.model.Event
import kotlinx.coroutines.flow.Flow

/**
 * PagingSource
 * primeiro parametro é a key, que é a representação do paremetro do numero de paginas (offset) ou afins.
 * Segundo parametrod é o Value, que é o que ele vai retornar.
 * */

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>

    fun getCachedCharacters(
        query: String,
        orderBy: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Character>>

    suspend fun getComics(characterId: Int): List<Comic>

    suspend fun getEvents(characterId: Int): List<Event>
}