package com.blabla.dontruinyourlaundry.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.internal.ParcelableSparseArray
import kotlinx.parcelize.Parcelize

@Parcelize
data class SymbolForWashing(
    @DrawableRes val pictureId: Int,
    val meaningOfSymbol: String,
    var selected: Boolean = false
) : Parcelable