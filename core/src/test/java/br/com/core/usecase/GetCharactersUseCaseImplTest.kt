package br.com.core.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.core.data.repository.CharactersRepository
import br.com.core.data.repository.StorageRepository
import br.com.testing.MainCoroutineRule
import br.com.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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

    @Mock
    lateinit var storageRepository: StorageRepository

    // o que vamos testar
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)

    private val fakePagingData = PagingData.from(
        listOf(
            hero
        )
    )

    @Before
    fun setup() {
        getCharactersUseCase = GetCharactersUseCaseImpl(repository, storageRepository)
    }


    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {

            val pagingConfig = PagingConfig(20)
            val orderBy = "ascending"
            val query = "Spider"

            whenever(repository.getCachedCharacters(query, orderBy, pagingConfig))
                .thenReturn(flowOf(fakePagingData))

            whenever(storageRepository.sorting)
                .thenReturn(flowOf(orderBy))

            val result = getCharactersUseCase.invoke(
                GetCharactersUseCase.GetCharactersParams(query, pagingConfig)
            )
            verify(repository).getCachedCharacters(query, orderBy, pagingConfig)

            assertNotNull(result.first())
        }
}