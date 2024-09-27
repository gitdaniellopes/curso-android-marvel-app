package br.com.core.data.repository

import br.com.core.domain.model.CharacterPaging
import br.com.core.domain.model.Comic
import br.com.core.domain.model.Event

/**
 * Utilizando generics na interface, dessa forma quem for usar, vai passar que tipo de dados ele quer retornar.
 * fazemos isso por que a classe DataWrapperResponse, não esta visivel a nivel do core da aplicação.
 * */

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging

    suspend fun fetchComics(characterId: Int): List<Comic>

    suspend fun fetchEvents(characterId: Int): List<Event>
}