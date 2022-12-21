package com.blabla.dontruinyourlaundry.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListOfSymbolsForDataBase(
val listOfSymbols: List<SymbolForWashingDBO>
):Parcelable
