package com.example.marvelapp.framework.remote

import br.com.core.data.repository.CharactersRemoteDataSource
import com.example.marvelapp.framework.network.MarvelApi
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import javax.inject.Inject

/**
 * Implementações que são nossas, passamos apenas o @Inject constructor,
 * não precisamos criar nenhum modulo para isso.
 * */

class RetrofitCharactersDataSource @Inject constructor(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource<DataWrapperResponse> {

    override suspend fun fetchCharacters(queries: Map<String, String>): DataWrapperResponse {
       return marvelApi.getCharacters(queries)
    }
}