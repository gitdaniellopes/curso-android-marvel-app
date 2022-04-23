package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName
/**
 * @SerializedName("copyright") é importante usar essa anotação, mesmo que utilizamos o mesmo nome em nosso atributo,
 * com o que vai vim da API, pois como ofuscamos nosso codigo de maneira segura, pedimos para o proguard, nao ofuscar as anotações do Gson,
 * dessa forma, ele pode colocar o nome de atributo como A, que ele vai olhar @SerializedName contento o nome da propriedade que vem da API.
 * */

data class DataWrapperResponse(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("data")
    val data: DataContainerResponse
)
