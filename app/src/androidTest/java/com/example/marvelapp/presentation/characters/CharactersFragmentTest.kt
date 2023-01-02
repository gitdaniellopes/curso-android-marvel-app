package com.example.marvelapp.presentation.characters

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marvelapp.R
import com.example.marvelapp.extension.asJsonString
import com.example.marvelapp.framework.di.BaseUrlModule
import com.example.marvelapp.framework.di.CoroutinesModule
import com.example.marvelapp.launchFragmentInHiltContainer
import com.example.marvelapp.presentation.characters.adapters.CharactersViewHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@UninstallModules - substituir a vinculação de produção, para isso, usamos essa anotação e desativamos a classe
// assim, ele vai usar o modulo de vinculaão de teste, que no caso, seria BaseUrlModuleTest
@UninstallModules(BaseUrlModule::class, CoroutinesModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CharactersFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var server: MockWebServer

    //Primeiramente iniciar o fragmento de teste de forma isolada
    //fragmento vai ser iniciado antes dos testes que eu tiver
    @Before
    fun setup() {
        server = MockWebServer().apply {
            start(port = 8080)
        }
        launchFragmentInHiltContainer<CharactersFragment>()
    }

    //Vamos testar se vai ser exbido os personagens quando ela for criada.
    @Test
    fun shouldShowCharacters_whenViewIsCreated() {
        server.enqueue(MockResponse().setBody("characters_p1.json".asJsonString()))

        /*
        *   ViewMatchers permitem encontrar a visão na hierarquia de visão atual
            ViewActions permitem realizar ações nas visualizações
            ViewAssertions permitem afirmar o estado de uma visão
        * */

        //Em uma view que tem um id com recycleView, check, verifica, faça alguma asserção.
        //Verificamos se a view casa com o isDisplayed, verificamos se a recycleview no momento que a view for criada,
        //ela vai estar visivel.
        onView(
            withId(R.id.recycle_characters)
        ).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun shouldLoadMoreCharacters_whenNewPageIsRequested() {

        //Arrange
        with(server) {
            enqueue(MockResponse().setBody("characters_p1.json".asJsonString()))
            enqueue(MockResponse().setBody("characters_p2.json".asJsonString()))
        }

        // Action
        // Nesse recycleView que contem tal id, execure uma ação nele
        onView(
            withId(R.id.recycle_characters)
        ).perform(
            RecyclerViewActions
                .scrollToPosition<CharactersViewHolder>(20)
        )

        //Assert
        onView(
            withText("Amora")
        ).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun shouldShowErrorView_whenReceivesAnErrorFromApi() {
        //Arrange
        server.enqueue(MockResponse().setResponseCode(404))

        //Assert
        onView(
            withId(R.id.text_initial_loading_error)
        ).check(
            matches(isDisplayed())
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}