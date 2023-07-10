package com.blabla.dontruinyourlaundry.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedSymbolsDBO(
    val symbols: List<SymbolForWashingDBO>
) : Parcelable
