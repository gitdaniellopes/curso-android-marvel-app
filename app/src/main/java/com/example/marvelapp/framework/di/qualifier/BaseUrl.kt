package com.example.marvelapp.framework.di.qualifier

import javax.inject.Qualifier

//Criamos esse Qualifier para podemos dissociar duas BaseUrl
//Uma mokcada para teste e outra real de produção.
// Dessa forma, passamos para o retrofit a String baseUrl anotada com o @BaseUrl
//verificar na classe NetworkModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl