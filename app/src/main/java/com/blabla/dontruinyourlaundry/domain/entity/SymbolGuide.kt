package com.blabla.dontruinyourlaundry.domain.entity

import android.content.Context
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.blabla.dontruinyourlaundry.R
import kotlinx.parcelize.Parcelize

sealed class SymbolGuide {

    data class HeadName(val nameId: HeadNameSymbolGuide) : SymbolGuide()

    @Parcelize
    data class SymbolForWashing(
        @DrawableRes val pictureId: Int,
        val meaningOfSymbol: String,
        var selected: Boolean = false
    ) : Parcelable, SymbolGuide() {


        fun toSymbolForWashingDBO(context: Context?): SymbolForWashingDBO? {
            return SymbolForWashingDBO.values().find { enumItem ->
                context?.let { enumItem.getDescription(it) } == meaningOfSymbol
            }
        }
    }
}

enum class HeadNameSymbolGuide {
    WASHING,
    BLEACHING,
    DRYING,
    IRONING,
    DRYCLEANING;

    fun getTitleName(context: Context):String{
        return when(this){
            WASHING -> context.getString(R.string.title_washing)
            BLEACHING -> context.getString(R.string.title_bleaching)
            DRYING-> context.getString(R.string.title_drying)
            IRONING-> context.getString(R.string.title_ironing)
            DRYCLEANING-> context.getString(R.string.title_drycleaning)
        }
    }

}