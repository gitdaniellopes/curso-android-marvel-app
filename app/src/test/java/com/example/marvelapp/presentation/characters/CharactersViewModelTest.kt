package com.example.marvelapp.presentation.characters

import androidx.paging.PagingData
import br.com.core.usecase.GetCharactersUseCase
import br.com.testing.MainCoroutineRule
import br.com.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
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

/**
 * MockitoJUnitRunner, ele que vai executar os nossos testes.
 * */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    /*
     para garantir que tod codigo do nosso teste execute em uma mesma thred, forma sincrona.
     pois quando trabalhamos com coroutines, ele usa varias threds para fazer um determinado trabalho.
     utilizado para teste
     conseguimos pausar e executar novamente nossa coroutina

     agora estamos usando uma regra unica de uma funcao no modulo testing, para evitar fazer isso em cada teste
     */

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    //Jamais mockamos o que queremos testar ou validar, vamos colocar as dependencias que ele precisa.
    private lateinit var charactersViewModel: CharactersViewModel

    private val characterFactory = CharacterFactory()

    private val pagingDataCharacters = PagingData.from(
        listOf(
            characterFactory.create(CharacterFactory.Hero.ThreeDMan),
            characterFactory.create(CharacterFactory.Hero.ABom)
        )
    )

    // essa função vai ser chamada antes de cada função de teste ser chamada
    @Before
    fun setup() {
        charactersViewModel = CharactersViewModel(
            getCharactersUseCase,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    //validando a comunicação entre viewModel e useCase
    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() =
        runTest {

            //quando chamar o meu getCharactersUseCase, para qualquer valor que seja passado no meu invoke, eu consigo fazer essa validação depois
            // não necessariamente preciso passar os parametros que o invoke precisa, uma forma de facilitar a vida, usando o any()
            // e quando ele chamar o getCharactersUseCase, quero retornar alguma coisa.
            // preciso retornar a mesma coisa que o meu caso de uso retorna
            whenever(
                getCharactersUseCase.invoke(any())
            ).thenReturn(
                flowOf(
                    pagingDataCharacters
                )
            )
            val result = charactersViewModel.charactersPagingData("")
            //assertEquals(1, result.count())
            assertNotNull(result.first())

//            result.map { resultCharacter ->
//                expectedPagingData.map {
//                    assertEquals(it.name, resultCharacter.name)
//                }
//            }
        }

    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exception`() =
        runTest {

            //quando chamar o meu getCharactersUseCase, para qualquer valor que seja passado no meu invoke, eu consigo fazer essa validação depois
            // não necessariamente preciso passar os parametros que o invoke precisa, uma forma de facilitar a vida, usando o any()
            // e quando ele chamar o getCharactersUseCase, quero retornar alguma coisa.
            // preciso retornar a mesma coisa que o meu caso de uso retorna
            whenever(
                getCharactersUseCase.invoke(any())
            ).thenThrow(RuntimeException())

            charactersViewModel.charactersPagingData("")
        }

}