package com.blabla.dontruinyourlaundry.domain.entity

import android.content.Context
import android.os.Parcelable
import com.blabla.dontruinyourlaundry.R
import kotlinx.parcelize.Parcelize


sealed class SearchScreenItem {

    data class Title(val name: TitleSearchByParameterEnum) : SearchScreenItem()

    data class SearchParameter(
        val name: SearchParameterEnum,
        val selected: Boolean = false
    ) : SearchScreenItem()

}

enum class TitleSearchByParameterEnum {
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
            DRYCLEANING -> context.getString(R.string.title_dry_cleaning)
        }
    }

    fun getSelectionType(): SelectionType {
        return when (this) {
            CATEGORY -> SelectionType.MULTI
            WASHING -> SelectionType.SINGLE
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

    fun getTitle(): SearchScreenItem.Title {
        return when (this) {
            CLOTH, BED_SHEET, BATH, KITCHEN, REST -> SearchScreenItem.Title(
                TitleSearchByParameterEnum.CATEGORY
            )
            WASH_ALLOWED, WASH_NOT_ALLOWED, WASH_HAND -> SearchScreenItem.Title(
                TitleSearchByParameterEnum.WASHING
            )
            LAUNDRY_30, LAUNDRY_40, LAUNDRY_50, LAUNDRY_60, LAUNDRY_70, LAUNDRY_95 -> SearchScreenItem.Title(
                TitleSearchByParameterEnum.WASHING_MODE
            )
            DRY_ALLOWED, DRY_NOT_ALLOWED -> SearchScreenItem.Title(TitleSearchByParameterEnum.DRYING)
            IRON_ALLOWED,
            IRON_NOT_ALLOWED -> SearchScreenItem.Title(TitleSearchByParameterEnum.IRONING)
            BLEACH_ALLOWED,
            BLEACH_NOT_ALLOWED -> SearchScreenItem.Title(TitleSearchByParameterEnum.BLEACHING)
            DRYCLEAN_ALLOWED,
            DRYCLEAN_NOT_ALLOWED -> SearchScreenItem.Title(TitleSearchByParameterEnum.DRYCLEANING)
        }
    }

    fun getName(context: Context): String {
        return when (this) {
            CLOTH -> context.getString(R.string.clothing)
            BED_SHEET -> context.getString(R.string.bed_sheets)
            BATH -> context.getString(R.string.bath_stuf)
            KITCHEN -> context.getString(R.string.kitchen_stuf)
            REST -> context.getString(R.string.the_rest)
            WASH_NOT_ALLOWED, DRY_NOT_ALLOWED, IRON_NOT_ALLOWED, DRYCLEAN_NOT_ALLOWED ->
                context.getString(R.string.not_allowed)
            BLEACH_NOT_ALLOWED -> context.getString(R.string.not_allowed)
            DRY_ALLOWED, IRON_ALLOWED, DRYCLEAN_ALLOWED ->
                context.getString(R.string.allowed)
            BLEACH_ALLOWED -> context.getString(R.string.allowed)
            WASH_ALLOWED -> context.getString(R.string.washing_machine_allowed)
            WASH_HAND -> context.getString(R.string.only_hand)
            LAUNDRY_30 -> context.getString(R.string.thirty)
            LAUNDRY_40 -> context.getString(R.string.forty)
            LAUNDRY_50 -> context.getString(R.string.fifty)
            LAUNDRY_60 -> context.getString(R.string.sixty)
            LAUNDRY_70 -> context.getString(R.string.seventy)
            LAUNDRY_95 -> context.getString(R.string.ninety_five)
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
                SymbolForWashingDBO.WASH_HAND,
                SymbolForWashingDBO.WASH_HAND_30,
                SymbolForWashingDBO.WASH_HAND_40
            )
            WASH_NOT_ALLOWED -> mutableListOf(SymbolForWashingDBO.WASH_NOT_ALLOWED)
            LAUNDRY_30 -> mutableListOf(
                SymbolForWashingDBO.WASH_30,
                SymbolForWashingDBO.WASH_30_DOT,
                SymbolForWashingDBO.WASH_30_DOT_CARE,
                SymbolForWashingDBO.WASH_30_CARE,
                SymbolForWashingDBO.WASH_30_DOT_EXTRA_CARE,
                SymbolForWashingDBO.WASH_30_EXTRA_CARE
            )
            LAUNDRY_40 -> mutableListOf(
                SymbolForWashingDBO.WASH_40,
                SymbolForWashingDBO.WASH_40_DOT,
                SymbolForWashingDBO.WASH_40_CARE,
                SymbolForWashingDBO.WASH_40_DOT_CARE,
                SymbolForWashingDBO.WASH_40DOT_EXTRA_CARE,
                SymbolForWashingDBO.WASH_40_EXTRA_CARE
            )
            LAUNDRY_50 -> mutableListOf(
                SymbolForWashingDBO.WASH_50,
                SymbolForWashingDBO.WASH_50_DOT,
                SymbolForWashingDBO.WASH_50_CARE,
                SymbolForWashingDBO.WASH_50_DOT_CARE
            )
            LAUNDRY_60 -> mutableListOf(
                SymbolForWashingDBO.WASH_60,
                SymbolForWashingDBO.WASH_60_DOT,
                SymbolForWashingDBO.WASH_60_CARE,
                SymbolForWashingDBO.WASH_60_DOT_CARE
            )
            LAUNDRY_70 -> mutableListOf(SymbolForWashingDBO.WASH_70, SymbolForWashingDBO.WASH_70_DOT)
            LAUNDRY_95 -> mutableListOf(
                SymbolForWashingDBO.WASH_90,
                SymbolForWashingDBO.WASH_90_DOT,
                SymbolForWashingDBO.WASH_90_CARE,
                SymbolForWashingDBO.WASH_90_DOT_CARE
            )
            DRY_ALLOWED -> mutableListOf(
                SymbolForWashingDBO.DRY,
                SymbolForWashingDBO.DRY_40,
                SymbolForWashingDBO.DRY_60,
                SymbolForWashingDBO.DRY_80,
                SymbolForWashingDBO.DRY_40_CARE,
                SymbolForWashingDBO.DRY_40_EXTRA_CARE,
                SymbolForWashingDBO.DRY_60_CARE,
                SymbolForWashingDBO.DRY_NO_HEAT,
                SymbolForWashingDBO.DRY_LINE,
                SymbolForWashingDBO.DRY_SHADE,
                SymbolForWashingDBO.DRY_LINE_SHADE,
                SymbolForWashingDBO.DRY_DRIP,
                SymbolForWashingDBO.DRY_DRIP_SHADE,
                SymbolForWashingDBO.DRY_FLAT,
                SymbolForWashingDBO.DRY_FLAT_SHADE
            )
            DRY_NOT_ALLOWED -> mutableListOf(SymbolForWashingDBO.DRY_NOT_ALLOWED)
            IRON_ALLOWED -> mutableListOf(
                SymbolForWashingDBO.IRON,
                SymbolForWashingDBO.IRON_110,
                SymbolForWashingDBO.IRON_150,
                SymbolForWashingDBO.IRON_200
            )
            IRON_NOT_ALLOWED -> mutableListOf(
                SymbolForWashingDBO.IRON_NOT_ALLOWED,
                SymbolForWashingDBO.IRON_STEAM_NOT_ALLOWED
            )
            BLEACH_ALLOWED -> mutableListOf(
                SymbolForWashingDBO.BLEACH,
                SymbolForWashingDBO.BLEACH_NON_CHLORINE
            )
            BLEACH_NOT_ALLOWED -> mutableListOf(SymbolForWashingDBO.BLEACH_NOT_ALLOWED)
            DRYCLEAN_ALLOWED -> mutableListOf(
                SymbolForWashingDBO.DRY_CLEAN,
                SymbolForWashingDBO.DRY_CLEAN_A,
                SymbolForWashingDBO.DRY_CLEAN_F,
                SymbolForWashingDBO.DRY_CLEAN_LOW_HEAT,
                SymbolForWashingDBO.DRY_CLEAN_LOW_MOISTURE,
                SymbolForWashingDBO.DRY_CLEAN_SHORT,
                SymbolForWashingDBO.DRY_CLEAN_P,
                SymbolForWashingDBO.DRY_CLEAN_NO_STEAM
            )
            DRYCLEAN_NOT_ALLOWED -> mutableListOf(SymbolForWashingDBO.DRY_CLEAN_NOT_ALLOWED)
            else -> emptyList()
        }
    }
}



