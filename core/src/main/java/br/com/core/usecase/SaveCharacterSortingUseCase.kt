package br.com.core.usecase

import br.com.core.data.mapper.SortingMapper
import br.com.core.data.repository.StorageRepository
import br.com.core.usecase.base.CoroutinesDispatchers
import br.com.core.usecase.base.ResultStatus
import br.com.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SaveCharacterSortingUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(val sortingPair: Pair<String, String>)
}

class SaveCharacterSortingUseCaseImpl @Inject constructor(
    private val repository: StorageRepository,
    private val sortingMapper: SortingMapper,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<SaveCharacterSortingUseCase.Params, Unit>(), SaveCharacterSortingUseCase {

    override suspend fun doWork(params: SaveCharacterSortingUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.saveSorting(sorting = sortingMapper.pairToString(params.sortingPair))
            ResultStatus.Success(Unit)
        }
    }
}