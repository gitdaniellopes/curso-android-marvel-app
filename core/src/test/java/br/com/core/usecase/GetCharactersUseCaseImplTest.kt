package br.com.core.usecase

import androidx.paging.PagingConfig
import br.com.core.data.repository.CharactersRepository
import br.com.testing.MainCoroutineRule
import br.com.testing.model.CharacterFactory
import br.com.testing.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: CharactersRepository

    // o que vamos testar
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)

    private val fakePagingSource = PagingSourceFactory().create(
        listOf(
            hero
        )
    )

    @Before
    fun setup() {
        getCharactersUseCase = GetCharactersUseCaseImpl(repository)
    }


    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {

            whenever(repository.getCharacters(""))
                .thenReturn(fakePagingSource)

            val result = getCharactersUseCase.invoke(
                GetCharactersUseCase.GetCharactersParams("", PagingConfig(20))
            )
            verify(repository).getCharacters("")

            assertNotNull(result.first())
        }
}