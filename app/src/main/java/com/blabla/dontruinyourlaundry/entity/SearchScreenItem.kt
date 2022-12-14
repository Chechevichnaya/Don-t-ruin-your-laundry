package com.blabla.dontruinyourlaundry.entity

import android.content.Context
import com.blabla.dontruinyourlaundry.R

sealed class SearchScreenItem {

    data class Title(val name: String) : SearchScreenItem()

    data class SearchParameter(
        val name: SearchParameterEnum,
        val selected: Boolean = false,
        val titleName: String
    ) : SearchScreenItem() {

        fun getCategory(): CategoryEnum {
             return when(this.name){
                SearchParameterEnum.CLOTH -> CategoryEnum.CLOTH
                SearchParameterEnum.BED_SHEET -> CategoryEnum.BAD_SHEETS
                SearchParameterEnum.KITCHEN -> CategoryEnum.KITCHEN
                SearchParameterEnum.BATH -> CategoryEnum.BATH
                else -> CategoryEnum.REST
            }
        }


    }

}

enum class SearchParameterEnum{
    CLOTH,
    BED_SHEET,
    BATH,
    KITCHEN,
    REST,
    WASH_ALLOWED,
    WASH_NOT_ALLOWED,
    WASH_HAND,
    LAUNDRY_30,
    LAUNDRY_40,
    LAUNDRY_50,
    LAUNDRY_60,
    LAUNDRY_70,
    LAUNDRY_95,
    DRY_ALLOWED,
    DRY_NOT_ALLOWED,
    IRON_ALLOWED,
    IRON_NOT_ALLOWED;

    fun getName(context: Context):String{
        return when (this) {
            CLOTH -> context.getString(R.string.cloth)
            BED_SHEET -> context.getString(R.string.bed_sheets)
            BATH -> context.getString(R.string.bath_stuf)
            KITCHEN -> context.getString(R.string.kitchen_stuf)
            REST -> context.getString(R.string.the_rest)
            WASH_NOT_ALLOWED, DRY_NOT_ALLOWED, IRON_NOT_ALLOWED -> context.getString(R.string.not_allowed)
            WASH_ALLOWED, DRY_ALLOWED, IRON_ALLOWED -> context.getString(R.string.allowed)
            WASH_HAND -> context.getString(R.string.only_hand)
            LAUNDRY_30 -> context.getString(R.string.thirty)
            LAUNDRY_40 -> context.getString(R.string.fourty)
            LAUNDRY_50 -> context.getString(R.string.fifty)
            LAUNDRY_60 -> context.getString(R.string.sixty)
            LAUNDRY_70 -> context.getString(R.string.seventy)
            LAUNDRY_95 -> context.getString(R.string.nintyfive)
        }
    }
}



