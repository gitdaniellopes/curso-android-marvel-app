package br.com.core.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.core.data.repository.CharactersRepository
import br.com.core.data.repository.StorageRepository
import br.com.core.domain.model.Character
import br.com.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

interface GetCharactersUseCase {
    operator fun invoke(params: GetCharactersParams): Flow<PagingData<Character>>

    data class GetCharactersParams(val query: String, val pagingConfig: PagingConfig)
}

class GetCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val storageRepository: StorageRepository
) : PagingUseCase<GetCharactersUseCase.GetCharactersParams, Character>(),
    GetCharactersUseCase {

    override fun createFlowObservable(params: GetCharactersUseCase.GetCharactersParams): Flow<PagingData<Character>> {

        val orderBy = runBlocking { storageRepository.sorting.first() }

        return charactersRepository.getCachedCharacters(params.query, orderBy, params.pagingConfig)
    }
}