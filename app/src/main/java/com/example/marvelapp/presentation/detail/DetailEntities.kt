package com.example.marvelapp.presentation.detail

import androidx.annotation.StringRes

/**
 * Essa classe será um objeto de view
 * */

data class DetailChildVE(
    val id: Int,
    val imageUrl: String
)

data class DetailParentVE(

    @StringRes
    val categoryStringResId: Int,
    val detailChildList: List<DetailChildVE> = listOf()
)