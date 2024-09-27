package com.example.marvelapp.presentation.detail

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @keep - vai fazer com que o proguard não ofusque essa classe
 *
 * Essa classe me permite ter mais flexibilidade para passar informações para outras telas.
 * assim, não preciso ir no grafico de navegação e adicionar mais um argumento.
 * */

@Keep
@Parcelize
data class DetailViewArg(
    val characterId: Int,
    val name: String,
    val imageUrl: String
) : Parcelable
