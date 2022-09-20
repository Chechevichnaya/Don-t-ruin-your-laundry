package com.blabla.dontruinyourlaundry.entity

import android.os.Parcelable
import com.blabla.dontruinyourlaundry.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Category(
    val imageResId: Int,
    val nameId: Int,
) : Parcelable {
    CLOTH(R.drawable.pyjamas, R.string.clothing),
    BAD_SHEETS(R.drawable.single_bed, R.string.bed_sheets),
    BATH(R.drawable.bathroom, R.string.bath_stuf),
    KITCHEN(R.drawable.kitchen, R.string.kitchen_stuf),
    REST(R.drawable.blanket, R.string.the_rest)
}





