package com.blabla.dontruinyourlaundry.domain.entity

import android.content.Context
import android.os.Parcelable
import com.blabla.dontruinyourlaundry.R
import kotlinx.parcelize.Parcelize


sealed class SearchScreenItem {

    data class Title(val name: TitleEnum) : SearchScreenItem()

    data class SearchParameter(
        val name: SearchParameterEnum,
        val selected: Boolean = false
//        val titleName: TitleEnum
    ) : SearchScreenItem() {

//        fun getCategory(): CategoryEnum {
//            return when (this.name) {
//                SearchParameterEnum.CLOTH -> CategoryEnum.CLOTH
//                SearchParameterEnum.BED_SHEET -> CategoryEnum.BAD_SHEETS
//                SearchParameterEnum.KITCHEN -> CategoryEnum.KITCHEN
//                SearchParameterEnum.BATH -> CategoryEnum.BATH
//                else -> CategoryEnum.REST
//            }
//        }


    }

}

enum class TitleEnum {
    CATEGORY,
    WASHING,
    WASHING_MODE,
    DRYING,
    IRONING,
    BLEACHING,
    DRYCLEANING;

    fun getTitleName(context: Context): String {
        return when (this) {
            CATEGORY -> context.getString(R.string.title_category)
            WASHING -> context.getString(R.string.title_washing)
            WASHING_MODE -> context.getString(R.string.title_wash_mode)
            DRYING -> context.getString(R.string.title_drying)
            IRONING -> context.getString(R.string.title_ironing)
            BLEACHING -> context.getString(R.string.title_bleaching)
            DRYCLEANING -> context.getString(R.string.title_drycleaning)
        }
    }

    fun getSelectionType(): SelectionType {
        return when(this){
            CATEGORY -> SelectionType.MULTI
            WASHING ->  SelectionType.SINGLE
            WASHING_MODE -> SelectionType.MULTI
            DRYING -> SelectionType.SINGLE
            IRONING -> SelectionType.SINGLE
            BLEACHING -> SelectionType.SINGLE
            DRYCLEANING -> SelectionType.SINGLE
        }
    }

    fun getParameters(): List<SearchParameterEnum> {
        return when (this) {
            CATEGORY -> mutableListOf(
                SearchParameterEnum.CLOTH,
                SearchParameterEnum.BED_SHEET,
                SearchParameterEnum.BATH,
                SearchParameterEnum.KITCHEN,
                SearchParameterEnum.REST
            )
            WASHING -> mutableListOf(
                SearchParameterEnum.WASH_ALLOWED,
                SearchParameterEnum.WASH_NOT_ALLOWED,
                SearchParameterEnum.WASH_HAND
            )
            WASHING_MODE
            -> mutableListOf(
                SearchParameterEnum.LAUNDRY_30,
                SearchParameterEnum.LAUNDRY_40,
                SearchParameterEnum.LAUNDRY_50,
                SearchParameterEnum.LAUNDRY_60,
                SearchParameterEnum.LAUNDRY_70,
                SearchParameterEnum.LAUNDRY_95
            )
            DRYING
            -> mutableListOf(
                SearchParameterEnum.DRY_ALLOWED,
                SearchParameterEnum.DRY_NOT_ALLOWED
            )
            IRONING
            -> mutableListOf(
                SearchParameterEnum.IRON_ALLOWED,
                SearchParameterEnum.IRON_NOT_ALLOWED
            )
            BLEACHING
            -> mutableListOf(
                SearchParameterEnum.BLEACH_ALLOWED,
                SearchParameterEnum.BLEACH_NOT_ALLOWED
            )
            DRYCLEANING
            -> mutableListOf(
                SearchParameterEnum.DRYCLEAN_ALLOWED,
                SearchParameterEnum.DRYCLEAN_NOT_ALLOWED
            )
        }
    }
}

@Parcelize
enum class SearchParameterEnum : Parcelable {
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
    IRON_NOT_ALLOWED,
    BLEACH_ALLOWED,
    BLEACH_NOT_ALLOWED,
    DRYCLEAN_ALLOWED,
    DRYCLEAN_NOT_ALLOWED;

    fun getCategory(): CategoryEnum? {
            return when (this) {
                CLOTH -> CategoryEnum.CLOTH
                BED_SHEET -> CategoryEnum.BAD_SHEETS
                KITCHEN -> CategoryEnum.KITCHEN
                BATH -> CategoryEnum.BATH
                REST -> CategoryEnum.REST
                else -> null
            }
        }

    fun getName(context: Context): String {
        return when (this) {
            CLOTH -> context.getString(R.string.cloth)
            BED_SHEET -> context.getString(R.string.bed_sheets)
            BATH -> context.getString(R.string.bath_stuf)
            KITCHEN -> context.getString(R.string.kitchen_stuf)
            REST -> context.getString(R.string.the_rest)
            WASH_NOT_ALLOWED, DRY_NOT_ALLOWED, IRON_NOT_ALLOWED, DRYCLEAN_NOT_ALLOWED ->
                context.getString(R.string.not_allowed)
            BLEACH_NOT_ALLOWED -> context.getString(R.string.not_allowed1)
            DRY_ALLOWED, IRON_ALLOWED, DRYCLEAN_ALLOWED ->
                context.getString(R.string.allowed)
            BLEACH_ALLOWED -> context.getString(R.string.allowed1)
            WASH_ALLOWED -> context.getString(R.string.washing_machine_allowed)
            WASH_HAND -> context.getString(R.string.only_hand)
            LAUNDRY_30 -> context.getString(R.string.thirty)
            LAUNDRY_40 -> context.getString(R.string.fourty)
            LAUNDRY_50 -> context.getString(R.string.fifty)
            LAUNDRY_60 -> context.getString(R.string.sixty)
            LAUNDRY_70 -> context.getString(R.string.seventy)
            LAUNDRY_95 -> context.getString(R.string.nintyfive)
        }
    }

    fun getAttachedSymbols(): List<SymbolForWashingDBO> {
        return when (this) {
            WASH_ALLOWED -> mutableListOf(SymbolForWashingDBO.WASH) +
                    LAUNDRY_30.getAttachedSymbols() +
                    LAUNDRY_40.getAttachedSymbols() +
                    LAUNDRY_40.getAttachedSymbols() +
                    LAUNDRY_50.getAttachedSymbols() +
                    LAUNDRY_60.getAttachedSymbols() +
                    LAUNDRY_70.getAttachedSymbols() +
                    LAUNDRY_95.getAttachedSymbols()
            WASH_HAND -> mutableListOf(
                SymbolForWashingDBO.WASHHAND,
                SymbolForWashingDBO.WASHHAND30,
                SymbolForWashingDBO.WASHHAND40
            )
            WASH_NOT_ALLOWED -> mutableListOf(SymbolForWashingDBO.WASHNOTALLOWED)
            LAUNDRY_30 -> mutableListOf(
                SymbolForWashingDBO.WASH30,
                SymbolForWashingDBO.WASH30DOT,
                SymbolForWashingDBO.WASH30DOTCARE,
                SymbolForWashingDBO.WASH30CARE,
                SymbolForWashingDBO.WASH30DOTEXTRACARE,
                SymbolForWashingDBO.WASH30EXTRACARE
            )
            LAUNDRY_40 -> mutableListOf(
                SymbolForWashingDBO.WASH40,
                SymbolForWashingDBO.WASH40DOT,
                SymbolForWashingDBO.WASH40CARE,
                SymbolForWashingDBO.WASH40DOTCARE,
                SymbolForWashingDBO.WASH40DOTEXTRACARE,
                SymbolForWashingDBO.WASH40EXTRACARE
            )
            LAUNDRY_50 -> mutableListOf(
                SymbolForWashingDBO.WASH50,
                SymbolForWashingDBO.WASH50DOT,
                SymbolForWashingDBO.WASH50CARE,
                SymbolForWashingDBO.WASH50DOTCARE
            )
            LAUNDRY_60 -> mutableListOf(
                SymbolForWashingDBO.WASH60,
                SymbolForWashingDBO.WASH60DOT,
                SymbolForWashingDBO.WASH60CARE,
                SymbolForWashingDBO.WASH60DOTCARE
            )
            LAUNDRY_70 -> mutableListOf(SymbolForWashingDBO.WASH70, SymbolForWashingDBO.WASH70DOT)
            LAUNDRY_95 -> mutableListOf(
                SymbolForWashingDBO.WASH90,
                SymbolForWashingDBO.WASH90DOT,
                SymbolForWashingDBO.WASH90CARE,
                SymbolForWashingDBO.WASH90DOTCARE
            )
            DRY_ALLOWED -> mutableListOf(
                SymbolForWashingDBO.DRY,
                SymbolForWashingDBO.DRY40,
                SymbolForWashingDBO.DRY60,
                SymbolForWashingDBO.DRY80,
                SymbolForWashingDBO.DRY40CARE,
                SymbolForWashingDBO.DRY40EXTRACARE,
                SymbolForWashingDBO.DRY60CARE,
                SymbolForWashingDBO.DRYNOHEAT,
                SymbolForWashingDBO.DRYLINE,
                SymbolForWashingDBO.DRYSHADE,
                SymbolForWashingDBO.DRYLINESHADE,
                SymbolForWashingDBO.DRYDRIP,
                SymbolForWashingDBO.DRYDRIPSHADE,
                SymbolForWashingDBO.DRYFLAT,
                SymbolForWashingDBO.DRYFLATSHADE
            )
            DRY_NOT_ALLOWED -> mutableListOf(SymbolForWashingDBO.DRYNOTALLOWED)
            IRON_ALLOWED -> mutableListOf(
                SymbolForWashingDBO.IRON,
                SymbolForWashingDBO.IRON110,
                SymbolForWashingDBO.IRON150,
                SymbolForWashingDBO.IRON200
            )
            IRON_NOT_ALLOWED -> mutableListOf(
                SymbolForWashingDBO.IRONNOTALLOWED,
                SymbolForWashingDBO.IRONSTEAMNOTALLOWED
            )
            else -> {
                emptyList()
            }
        }
    }


}



