package br.com.core.usecase

import br.com.core.data.repository.CharactersRepository
import br.com.core.domain.model.Comic
import br.com.core.domain.model.Event
import br.com.core.usecase.base.AppCoroutinesDispatchers
import br.com.core.usecase.base.ResultStatus
import br.com.core.usecase.base.UseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Importante:
 * Vamos buscar as comics e os events paralelamente com Coroutines
 * */
interface GetCharacterCategoriesUseCase {

    operator fun invoke(params: GetComicsParams): Flow<ResultStatus<Pair<List<Comic>, List<Event>>>>
    data class GetComicsParams(val characterId: Int)
}

class GetCharacterCategoriesUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository,
    private val dispatchers: AppCoroutinesDispatchers
) : GetCharacterCategoriesUseCase,
    UseCase<GetCharacterCategoriesUseCase.GetComicsParams, Pair<List<Comic>, List<Event>>>() {

    override suspend fun doWork(params: GetCharacterCategoriesUseCase.GetComicsParams): ResultStatus<Pair<List<Comic>, List<Event>>> {
       return withContext(dispatchers.io) {
            val comicsDeferred = async { repository.getComics(characterId = params.characterId) }
            val eventsDeferred = async { repository.getEvents(characterId = params.characterId) }

            val comics = comicsDeferred.await()
            val events = eventsDeferred.await()

           //posso passar assim com o pair, ou - comics to events, mesma coisa.
            //val pair = Pair(comics, events)

            ResultStatus.Success(comics to events)
        }
    }
}