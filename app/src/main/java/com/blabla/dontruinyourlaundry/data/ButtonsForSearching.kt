package com.blabla.dontruinyourlaundry.data

import android.os.Parcelable
import com.blabla.dontruinyourlaundry.R
import kotlinx.parcelize.Parcelize


data class ButtonsForSearching(
    val name:String,
    var selected: Boolean = false

)
