package com.blabla.dontruinyourlaundry.data

import android.os.Parcelable
import com.blabla.dontruinyourlaundry.entity.Category
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardInfo(
    var name: String,
    var picture: String,
    var listOfSymbols: MutableList<SymbolForWashing>,
    var category: Category

): Parcelable