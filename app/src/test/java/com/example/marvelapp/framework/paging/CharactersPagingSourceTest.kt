package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import br.com.core.data.repository.CharactersRemoteDataSource
import br.com.core.domain.model.Character
import br.com.testing.MainCoroutineRule
import br.com.testing.model.CharacterFactory
import com.example.marvelapp.factory.response.CharacterPagingFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersPagingSourceTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    //mokando o repositorio para satisfazer a dependencia do CharactersPagingSource
    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource

    private val characterFactory = CharacterFactory()

    private val characterPagingFactory = CharacterPagingFactory()

    // o que eu quero testar
    private lateinit var charactersPagingSource: CharactersPagingSource

    @Before
    fun setup() {
        charactersPagingSource = CharactersPagingSource(
            remoteDataSource = remoteDataSource,
            query = ""
        )
    }

    /**
     * Padrão AAA - para o teste ficar mais legivel.
     * Arrange - preparação, etapa que prepara os mocks,
     * Act - função a ser testata
     * Assert - fazer as asserções, verificar o que esta acontecendo, o que foi obtido com o resultado da função a ser testada.
     * */

    @Test
    fun `should return a success load result when load is called`() = runTest {

        //Arrange
        val create = characterPagingFactory.create()
        whenever(remoteDataSource.fetchCharacters(any()))
            .thenReturn(create)

        //Act
        val result = charactersPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        //Assert

        val expected = listOf(
            characterFactory.create(CharacterFactory.Hero.ThreeDMan),
            characterFactory.create(CharacterFactory.Hero.ABom)
        )

        assertEquals(
            PagingSource.LoadResult.Page(
                data = expected,
                prevKey = null,
                nextKey = 20
            ),
            result
        )
    }

    @Test
    fun `should return a error load result when load is called`() = runTest {

        //Arrange

        //sempre que meu remoteDataSource.fetchCharacters("") for passado com o queries any (qualquer alor), for chamado
        //quero: retornar uma exceção.

        val exception = RuntimeException()

        whenever(remoteDataSource.fetchCharacters(any()))
            .thenThrow(exception)

        //Act
        val result = charactersPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        //Assert
        // Quero verificar se o o LoadResult.Error do PagingSource vai ser chamado
        assertEquals(
            PagingSource.LoadResult.Error<Int, Character>(exception),
            result
        )
    }
}