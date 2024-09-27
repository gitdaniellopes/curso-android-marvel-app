package br.com.core.usecase

import br.com.core.data.repository.FavoritesRepository
import br.com.core.domain.model.Character
import br.com.core.usecase.base.CoroutinesDispatchers
import br.com.core.usecase.base.ResultStatus
import br.com.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AddFavoriteUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(val characterId: Int, val name: String, val imageUrl: String)
}

class AddFavoriteUseCaseImpl @Inject constructor(
    private val repository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<AddFavoriteUseCase.Params, Unit>(), AddFavoriteUseCase {

    override suspend fun doWork(params: AddFavoriteUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.saveFavorite(
                Character(
                    id = params.characterId,
                    name = params.name,
                    imageUrl = params.imageUrl
                )
            )
            ResultStatus.Success(Unit)
        }
    }
}