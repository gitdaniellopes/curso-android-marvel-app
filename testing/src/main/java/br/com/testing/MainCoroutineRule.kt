package br.com.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
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
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
): TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    // precisamos limpar o Dispatcher ao final de cada teste
    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}