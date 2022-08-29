package com.blabla.dontruinyourlaundry.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SymbolForWashing(
    @DrawableRes val pictureId: Int,
    val meaningOfSymbol: String,
    val selected: Boolean = false
)
