package br.com.testing

import br.com.core.usecase.base.CoroutinesDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Criamos um modulo de teste onde podemos utilizar suas funções que serão comums para ambos os modulos no projeto.
 * Nesse caso, estamos criando regras para o Junit que serão usadas para os demais modulos, dessa forma, temos
 * a capacidade de isolar as funcoes de testes e evitar codigo repetitivo.
 *
 * Esse codigo foi tirado de um codlab do google, de como usar testes com corroutines e viewmodel
 * */

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
) : TestWatcher() {

    val testDispatcherProvider = object : CoroutinesDispatchers {
        override fun default(): CoroutineDispatcher = testDispatcher
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun main(): CoroutineDispatcher = testDispatcher
        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}