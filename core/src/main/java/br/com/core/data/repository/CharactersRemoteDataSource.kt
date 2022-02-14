package br.com.core.data.repository

/**
 * Utilizando generics na interface, dessa forma quem for usar, vai passar que tipo de dados ele quer retornar.
 * fazemos isso por que a classe DataWrapperResponse, não esta visivel a nivel do core da aplicação.
 * */

interface CharactersRemoteDataSource<T> {

    suspend fun fetchCharacters(queries: Map<String, String>): T
}