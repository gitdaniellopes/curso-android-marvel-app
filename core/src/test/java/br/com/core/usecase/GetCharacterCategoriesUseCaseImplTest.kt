package br.com.core.usecase

import br.com.core.data.repository.CharactersRepository
import br.com.core.usecase.base.ResultStatus
import br.com.testing.MainCoroutineRule
import br.com.testing.model.CharacterFactory
import br.com.testing.model.ComicFactory
import br.com.testing.model.EventFactory
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharacterCategoriesUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase

    @Mock
    lateinit var repository: CharactersRepository

    private val character = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    @Before
    fun setup() {
        getCharacterCategoriesUseCase = GetCharacterCategoriesUseCaseImpl(
            repository = repository,
            dispatchers = mainCoroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `should return Success from ResultStatus when get both requests return success`() =
        runTest {
            // Arrange

            //Sempre que o meu getComics for chamado, eu quero retornar...
            whenever(repository.getComics(characterId = character.id)).thenReturn(comics)
            whenever(repository.getEvents(characterId = character.id)).thenReturn(events)

            //Act
            val result = getCharacterCategoriesUseCase.invoke(
                GetCharacterCategoriesUseCase.GetCategoriesParams(
                    characterId = character.id
                )
            )

            // Assert
            //toList por que sempre retorna mais de um resultado
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])

            //Verifica se a instancia de quem esta na posisão 1 é do tipo Success
            // Como Success é uma classe, usamos o assertTrue para verificar
            assertTrue(resultList[1] is ResultStatus.Success)
        }

    @Test
    fun `should return Error from ResultStatus when get events request returns error`() =
        runTest {
            // Arrange

            //Sempre que o meu getComics for chamado, eu quero retornar...
            whenever(repository.getEvents(characterId = character.id)).thenAnswer { throw Throwable() }

            //Act
            val result = getCharacterCategoriesUseCase.invoke(
                GetCharacterCategoriesUseCase.GetCategoriesParams(
                    characterId = character.id
                )
            )

            // Assert
            //toList por que sempre retorna mais de um resultado
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])

            //Verifica se a instancia de quem esta na posisão 1 é do tipo Success
            // Como Success é uma classe, usamos o assertTrue para verificar
            assertTrue(resultList[1] is ResultStatus.Error)
            assertFalse(resultList[1] is ResultStatus.Success)
        }

    @Test
    fun `should return Error from ResultStatus when get comics request returns error`() =
        runTest {
            // Arrange

            //Sempre que o meu getComics for chamado, eu quero retornar...
            whenever(repository.getComics(characterId = character.id)).thenAnswer { throw Throwable() }

            //Act
            val result = getCharacterCategoriesUseCase.invoke(
                GetCharacterCategoriesUseCase.GetCategoriesParams(
                    characterId = character.id
                )
            )

            // Assert
            //toList por que sempre retorna mais de um resultado
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])

            //Verifica se a instancia de quem esta na posisão 1 é do tipo Success
            // Como Success é uma classe, usamos o assertTrue para verificar
            assertTrue(resultList[1] is ResultStatus.Error)
            assertFalse(resultList[1] is ResultStatus.Success)
        }
}