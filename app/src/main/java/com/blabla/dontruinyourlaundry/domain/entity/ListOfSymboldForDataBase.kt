package com.blabla.dontruinyourlaundry.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListOfSymbolsForDataBase(
val listOfSymbols: List<SymbolForWashingDBO>
):Parcelable
