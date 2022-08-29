package com.blabla.dontruinyourlaundry.data

import androidx.annotation.DrawableRes

data class Cardinfo(
    val name: String,
    @DrawableRes
    val picture: Int,
    var listOfSimbols: MutableList<SymbolForWashing>
) {
}