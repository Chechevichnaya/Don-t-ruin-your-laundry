package com.blabla.dontruinyourlaundry.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.blabla.dontruinyourlaundry.R
import com.google.android.material.internal.ParcelableSparseArray
import kotlinx.parcelize.Parcelize

@Parcelize
data class SymbolForWashing(
    @DrawableRes val pictureId: Int,
    val meaningOfSymbol: String,
    var selected: Boolean = false
) : Parcelable


@Parcelize
class Symbols: ArrayList<SymbolForWashing>(), Parcelable

enum class SymbolsForGuide(val text: String) {
    WASH( "fds"),
    NOTWASH("gjhgj");

//    fun toSymbolForWashing(): SymbolForWashing {
//        val resourceId = when (this) {
//            WASH -> R.id.delete_button
//            NOTWASH -> TODO()
//        }
//        SymbolForWashing(
//            pictureId = resourceId,
//            meaningOfSymbol = this.text
//        )
//    }
}

