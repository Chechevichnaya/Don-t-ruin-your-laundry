package com.blabla.dontruinyourlaundry.domain.entity

import android.content.Context
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.blabla.dontruinyourlaundry.R
import kotlinx.parcelize.Parcelize

sealed class SymbolGuideItem {

    data class HeaderName(val nameId: HeaderNameSymbolGuide) : SymbolGuideItem()

    @Parcelize
    data class SymbolForWashing(
        @DrawableRes val pictureId: Int,
        val meaningOfSymbol: String,
        var selected: Boolean = false
    ) : Parcelable, SymbolGuideItem() {

        fun toSymbolForWashingDBO(context: Context?): SymbolForWashingDBO? {
            return SymbolForWashingDBO.values().find { enumItem ->
                context?.let { enumItem.getDescription(it) } == meaningOfSymbol
            }
        }
    }
}

enum class HeaderNameSymbolGuide {
    WASHING,
    BLEACHING,
    DRYING,
    TUMBLE_DRYING,
    IRONING,
    DRY_CLEANING,
    WET_CLEANING,
    WRINGING;

    fun getTitleName(context: Context):String{
        return when(this){
            WASHING -> context.getString(R.string.title_washing)
            BLEACHING -> context.getString(R.string.title_bleaching)
            DRYING-> context.getString(R.string.title_drying)
            TUMBLE_DRYING -> context.getString(R.string.title_tumble_drying)
            IRONING-> context.getString(R.string.title_ironing)
            DRY_CLEANING-> context.getString(R.string.title_dry_cleaning)
            WET_CLEANING -> context.getString(R.string.title_wet_cleaning)
            WRINGING -> context.getString(R.string.wringing)
        }
    }
}