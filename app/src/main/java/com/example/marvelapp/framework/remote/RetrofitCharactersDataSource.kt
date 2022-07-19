package com.example.marvelapp.framework.remote

import br.com.core.data.repository.CharactersRemoteDataSource
import br.com.core.domain.model.CharacterPaging
import br.com.core.domain.model.Comic
import br.com.core.domain.model.Event
import com.example.marvelapp.framework.network.MarvelApi
import com.example.marvelapp.framework.network.response.toCharacterModel
import com.example.marvelapp.framework.network.response.toComicModel
import com.example.marvelapp.framework.network.response.toEventModel
import javax.inject.Inject

/**
 * Implementações que são nossas, passamos apenas o @Inject constructor,
 * não precisamos criar nenhum modulo para isso.
 * */

class RetrofitCharactersDataSource @Inject constructor(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource {

    override suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging {
        val data = marvelApi.getCharacters(queries).data
        val characters = data.results.map {
            it.toCharacterModel()
        }
        return CharacterPaging(
            offset = data.offset,
            total = data.total,
            characters = characters
        )
    }

    override suspend fun fetchComics(characterId: Int): List<Comic> {
        val data = marvelApi.getComics(characterId).data.results.map {
            it.toComicModel()
        }
        return data
    }

    override suspend fun fetchEvents(characterId: Int): List<Event> {
        return marvelApi.getEvents(characterId = characterId).data.results.map {
            it.toEventModel()
        }
    }
}