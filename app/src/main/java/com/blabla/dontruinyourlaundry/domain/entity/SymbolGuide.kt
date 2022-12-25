package com.blabla.dontruinyourlaundry.domain.entity

import android.content.Context
import android.os.Parcelable
import androidx.annotation.DrawableRes

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
    data class ButtonAddNewSymbol(@DrawableRes val icon: Int,  val text: String): SymbolGuide()
}

enum class HeadNameSymbolGuide(val head: String) {
    WASHING("Стирка"),
    BLEACHING("Отбеливание"),
    DRYING("Сушка"),
    IRONING("Глажка"),
    DRYCLEANING("Сухая чистка"),
    WRINGLING("Скручивание")

}