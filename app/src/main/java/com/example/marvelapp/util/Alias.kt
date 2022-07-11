package com.example.marvelapp.util

import android.view.View
import br.com.core.domain.model.Character

typealias OnCharacterItemClick = (
    character: Character,
    view: View
) -> Unit