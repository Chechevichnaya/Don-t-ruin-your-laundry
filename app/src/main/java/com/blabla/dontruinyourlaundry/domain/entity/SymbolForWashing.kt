package com.blabla.dontruinyourlaundry.domain.entity

import android.content.Context
import android.os.Parcelable
import com.blabla.dontruinyourlaundry.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListOfSymbols(val list: List<SymbolGuideItem.SymbolForWashing>) : Parcelable

@Parcelize
enum class SymbolForWashingDBO : Parcelable {
    WASH,
    WASH_NOT_ALLOWED,
    WASH_HAND,
    WASH_HAND_30,
    WASH_HAND_40,
    WASH_30,
    WASH_40,
    WASH_50,
    WASH_60,
    WASH_70,
    WASH_90,
    WASH_30_CARE,
    WASH_30_EXTRA_CARE,
    WASH_40_CARE,
    WASH_40_EXTRA_CARE,
    WASH_50_CARE,
    WASH_60_CARE,
    WASH_90_CARE,
    WASH_30_DOT,
    WASH_40_DOT,
    WASH_50_DOT,
    WASH_60_DOT,
    WASH_70_DOT,
    WASH_90_DOT,
    WASH_30_DOT_CARE,
    WASH_30_DOT_EXTRA_CARE,
    WASH_40_DOT_CARE,
    WASH_40DOT_EXTRA_CARE,
    WASH_50_DOT_CARE,
    WASH_60_DOT_CARE,
    WASH_90_DOT_CARE,
    BLEACH,
    BLEACH_NON_CHLORINE,
    BLEACH_NOT_ALLOWED,
    BLEACH_NOT_ALLOWED_OLD,
    DRY,
    DRY_40,
    DRY_60,
    DRY_80,
    DRY_NO_HEAT,
    DRY_40_CARE,
    DRY_40_EXTRA_CARE,
    DRY_60_CARE,
    DRY_LINE,
    DRY_VERTICAL_LINE,
    DRY_SHADE,
    DRY_LINE_SHADE,
    DRY_DRIP,
    DRY_DRIP_SHADE,
    DRY_FLAT,
    DRY_FLAT_SHADE,
    DRY_NOT_ALLOWED,
    IRON,
    IRON_110,
    IRON_150,
    IRON_200,
    IRON_NOT_ALLOWED,
    IRON_STEAM_NOT_ALLOWED,
    DRY_CLEAN,
    DRY_CLEAN_A,
    DRY_CLEAN_P,
    DRY_CLEAN_P2,
    DRY_CLEAN_F,
    DRY_CLEAN_F2,
    DRY_CLEAN_W,
    DRY_CLEAN_W2,
    DRY_CLEAN_W2_LINE,
    DRY_CLEAN_SHORT,
    DRY_CLEAN_LOW_HEAT,
    DRY_CLEAN_LOW_MOISTURE,
    DRY_CLEAN_NO_STEAM,
    DRY_CLEAN_NOT_ALLOWED,
    DRY_WET_CLEAN_NOT_ALLOWED,
    WRINGING;

    fun getDescription(context: Context): String {
        return when (this) {
            WASH -> context.getString(R.string.wash)
            WASH_NOT_ALLOWED -> context.getString(R.string.wash_not_allowed)
            WASH_HAND -> context.getString(R.string.wash_hand)
            WASH_HAND_30 -> context.getString(R.string.wash_hand30)
            WASH_HAND_40 -> context.getString(R.string.wash_hand40)
            WASH_30 -> context.getString(R.string.wash30)
            WASH_40 -> context.getString(R.string.wash40)
            WASH_50 -> context.getString(R.string.wash50)
            WASH_60 -> context.getString(R.string.wash60)
            WASH_70 -> context.getString(R.string.wash70)
            WASH_90 -> context.getString(R.string.wash90)
            WASH_30_CARE -> context.getString(R.string.wash30_care)
            WASH_30_EXTRA_CARE -> context.getString(R.string.wash30_extracare)
            WASH_40_CARE -> context.getString(R.string.wash40_care)
            WASH_40_EXTRA_CARE -> context.getString(R.string.wash40_extracare)
            WASH_50_CARE -> context.getString(R.string.wash50_care)
            WASH_60_CARE -> context.getString(R.string.wash60_care)
            WASH_90_CARE -> context.getString(R.string.wash90_care)
            WASH_30_DOT -> context.getString(R.string.wash30)
            WASH_40_DOT -> context.getString(R.string.wash40)
            WASH_50_DOT -> context.getString(R.string.wash50)
            WASH_60_DOT -> context.getString(R.string.wash60)
            WASH_70_DOT -> context.getString(R.string.wash70)
            WASH_90_DOT -> context.getString(R.string.wash90)
            WASH_30_DOT_CARE -> context.getString(R.string.wash30_care)
            WASH_30_DOT_EXTRA_CARE -> context.getString(R.string.wash30_extracare)
            WASH_40_DOT_CARE -> context.getString(R.string.wash40_care)
            WASH_40DOT_EXTRA_CARE -> context.getString(R.string.wash40_extracare)
            WASH_50_DOT_CARE -> context.getString(R.string.wash50_care)
            WASH_60_DOT_CARE -> context.getString(R.string.wash60_care)
            WASH_90_DOT_CARE -> context.getString(R.string.wash90_care)
            BLEACH -> context.getString(R.string.bleach)
            BLEACH_NON_CHLORINE -> context.getString(R.string.bleach_non_chlor)
            BLEACH_NOT_ALLOWED -> context.getString(R.string.bleach_not_allowed)
            BLEACH_NOT_ALLOWED_OLD -> context.getString(R.string.bleach_not_allowed)
            DRY -> context.getString(R.string.dry)
            DRY_40 -> context.getString(R.string.dry40)
            DRY_60 -> context.getString(R.string.dry60)
            DRY_80 -> context.getString(R.string.dry80)
            DRY_NO_HEAT -> context.getString(R.string.dry_no_heat)
            DRY_40_CARE -> context.getString(R.string.dry40_care)
            DRY_40_EXTRA_CARE -> context.getString(R.string.dry40_extracare)
            DRY_60_CARE -> context.getString(R.string.dry60_care)
            DRY_LINE -> context.getString(R.string.dry_line)
            DRY_SHADE -> context.getString(R.string.dry_shade)
            DRY_LINE_SHADE -> context.getString(R.string.dry_line_shade)
            DRY_DRIP -> context.getString(R.string.dry_drip)
            DRY_DRIP_SHADE -> context.getString(R.string.dry_drip_shade)
            DRY_FLAT -> context.getString(R.string.dry_flat)
            DRY_FLAT_SHADE -> context.getString(R.string.dry_flat_shade)
            DRY_VERTICAL_LINE -> context.getString(R.string.dry_vertical_line)
            DRY_NOT_ALLOWED -> context.getString(R.string.dry_not_alloder)
            IRON -> context.getString(R.string.iron)
            IRON_110 -> context.getString(R.string.iron110)
            IRON_150 -> context.getString(R.string.iron150)
            IRON_200 -> context.getString(R.string.iron200)
            IRON_NOT_ALLOWED -> context.getString(R.string.iron_not_allowed)
            IRON_STEAM_NOT_ALLOWED -> context.getString(R.string.iron_steam_not_allowed)
            DRY_CLEAN -> context.getString(R.string.dry_clean)
            DRY_CLEAN_A -> context.getString(R.string.dry_cleana)
            DRY_CLEAN_P -> context.getString(R.string.dry_cleanp)
            DRY_CLEAN_P2 -> context.getString(R.string.dry_clean_p2)
            DRY_CLEAN_F -> context.getString(R.string.dry_cleanf)
            DRY_CLEAN_F2 -> context.getString(R.string.dry_clean_f2)
            DRY_CLEAN_W -> context.getString(R.string.dry_clean_w)
            DRY_CLEAN_W2 -> context.getString(R.string.dry_clean_w2)
            DRY_CLEAN_W2_LINE -> context.getString(R.string.dry_clean_w3)
            DRY_WET_CLEAN_NOT_ALLOWED -> context.getString(R.string.dry_clean_wet_not_allowed)
            DRY_CLEAN_SHORT -> context.getString(R.string.dry_clean_short)
            DRY_CLEAN_LOW_HEAT -> context.getString(R.string.dry_clean_lowheat)
            DRY_CLEAN_LOW_MOISTURE -> context.getString(R.string.dry_clean_low_water)
            DRY_CLEAN_NO_STEAM -> context.getString(R.string.dry_clean_no_steam)
            DRY_CLEAN_NOT_ALLOWED -> context.getString(R.string.dry_clean_not_allowed)
            WRINGING -> context.getString(R.string.wringing_text)
        }
    }

    fun getResIdIcon(): Int = when (this) {
        WASH -> R.drawable.wash
        WASH_NOT_ALLOWED -> R.drawable.wh_washing_not_allowed
        WASH_HAND -> R.drawable.wh_washing_hand
        WASH_HAND_30 -> R.drawable.wh_washing_hand_30deg
        WASH_HAND_40 -> R.drawable.wh_washing_hand_40deg
        WASH_30 -> R.drawable.wh_washing_30deg
        WASH_40 -> R.drawable.wh_washing_40deg
        WASH_50 -> R.drawable.wh_washing_50deg
        WASH_60 -> R.drawable.wh_washing_60deg
        WASH_70 -> R.drawable.wh_washing_70deg
        WASH_90 -> R.drawable.wh_washing_90deg
        WASH_30_CARE -> R.drawable.wh_washing_30deg_permanent_press
        WASH_30_EXTRA_CARE -> R.drawable.wh_washing_30deg_extra_care
        WASH_40_CARE -> R.drawable.wh_washing_40deg_permanent_press
        WASH_40_EXTRA_CARE -> R.drawable.wh_washing_40deg_extra_care
        WASH_50_CARE -> R.drawable.wh_washing_50deg_permanent_press
        WASH_60_CARE -> R.drawable.wh_washing_60deg_permanent_press
        WASH_90_CARE -> R.drawable.wh_washing_95deg_permanent_press
        WASH_30_DOT -> R.drawable.wh_washing_30deg_alt
        WASH_40_DOT -> R.drawable.wh_washing_40deg_alt
        WASH_50_DOT -> R.drawable.wh_washing_50deg_alt
        WASH_60_DOT -> R.drawable.wh_washing_60deg_alt
        WASH_70_DOT -> R.drawable.wh_washing_70deg_alt
        WASH_90_DOT -> R.drawable.wh_washing_95deg_alt
        WASH_30_DOT_CARE -> R.drawable.wh_washing_30deg_permanent_press_alt
        WASH_30_DOT_EXTRA_CARE -> R.drawable.wh_washing_30deg_extra_care_alt
        WASH_40_DOT_CARE -> R.drawable.wh_washing_40deg_permanent_press_alt
        WASH_40DOT_EXTRA_CARE -> R.drawable.wh_washing_40deg_extra_care_alt
        WASH_50_DOT_CARE -> R.drawable.wh_washing_50deg_permanent_press_alt
        WASH_60_DOT_CARE -> R.drawable.wh_washing_60deg_permanent_press_alt
        WASH_90_DOT_CARE -> R.drawable.wh_washing_95deg_permanent_press_alt
        BLEACH -> R.drawable.wh_bleaching
        BLEACH_NON_CHLORINE -> R.drawable.wh_bleaching_non_chlorine
        BLEACH_NOT_ALLOWED -> R.drawable.wh_bleaching_not_allowed_white
        BLEACH_NOT_ALLOWED_OLD -> R.drawable.wh_bleaching_not_allowed
        DRY -> R.drawable.wh_drying_tumble
        DRY_40 -> R.drawable.wh_drying_tumble_low_heat
        DRY_60 -> R.drawable.wh_drying_tumble_medium_heat
        DRY_80 -> R.drawable.wh_drying_tumble_high_heat
        DRY_NO_HEAT -> R.drawable.wh_drying_tumble_no_heat
        DRY_40_CARE -> R.drawable.wh_drying_tumble_low_heat_permanent_press
        DRY_40_EXTRA_CARE -> R.drawable.wh_drying_tumble_low_heat_extra_care
        DRY_60_CARE -> R.drawable.wh_drying_tumble_medium_heat_permanent_press
        DRY_LINE -> R.drawable.wh_drying_line_dry
        DRY_SHADE -> R.drawable.wh_drying_dry_shade
        DRY_LINE_SHADE -> R.drawable.wh_drying_line_dry_shade
        DRY_DRIP -> R.drawable.wh_drying_drip_dry
        DRY_DRIP_SHADE -> R.drawable.wh_drying_drip_dry_shade
        DRY_FLAT -> R.drawable.wh_drying_flat_dry
        DRY_FLAT_SHADE -> R.drawable.wh_drying_flat_dry_shade
        DRY_VERTICAL_LINE -> R.drawable.wh_drying_flat_dry2
        DRY_NOT_ALLOWED -> R.drawable.wh_drying_tumble_not_allowed
        IRON -> R.drawable.wh_ironing
        IRON_110 -> R.drawable.wh_ironing_low
        IRON_150 -> R.drawable.wh_ironing_medium
        IRON_200 -> R.drawable.wh_ironing_high
        IRON_NOT_ALLOWED -> R.drawable.wh_ironing_not_allowed
        IRON_STEAM_NOT_ALLOWED -> R.drawable.wh_ironing_steam_not_allowed
        DRY_CLEAN -> R.drawable.wh_drycleaning
        DRY_CLEAN_A -> R.drawable.wh_drycleaning_a
        DRY_CLEAN_P -> R.drawable.wh_drycleaning_p
        DRY_CLEAN_P2 -> R.drawable.wh_drycleaning_p_2
        DRY_CLEAN_F -> R.drawable.wh_drycleaning_f
        DRY_CLEAN_F2 -> R.drawable.wh_drycleaning_f_2
        DRY_CLEAN_W -> R.drawable.wh_drycleaning_w
        DRY_CLEAN_W2 -> R.drawable.wh_drycleaning_w_2
        DRY_CLEAN_W2_LINE -> R.drawable.wh_drycleaning_w_3
        DRY_CLEAN_SHORT -> R.drawable.wh_drycleaning_short_cycle
        DRY_CLEAN_LOW_HEAT -> R.drawable.wh_drycleaning_low_heat
        DRY_CLEAN_LOW_MOISTURE -> R.drawable.wh_drycleaning_reduced_moisture
        DRY_CLEAN_NO_STEAM -> R.drawable.wh_drycleaning_no_steam
        DRY_CLEAN_NOT_ALLOWED -> R.drawable.wh_drycleaning_not_allowed
        DRY_WET_CLEAN_NOT_ALLOWED -> R.drawable.wh_drycleaning_wetclean_not_allowed
        WRINGING -> R.drawable.wh_wringing_not_allowed

    }

    fun getTitle(): HeaderNameSymbolGuide {
        return when (this) {
            WASH, WASH_NOT_ALLOWED, WASH_HAND, WASH_HAND_30, WASH_HAND_40, WASH_30, WASH_40, WASH_50, WASH_60,
            WASH_70, WASH_90, WASH_30_CARE, WASH_30_EXTRA_CARE, WASH_40_CARE, WASH_40_EXTRA_CARE, WASH_50_CARE,
            WASH_60_CARE, WASH_90_CARE, WASH_30_DOT, WASH_40_DOT, WASH_50_DOT, WASH_60_DOT, WASH_70_DOT,
            WASH_90_DOT, WASH_30_DOT_CARE, WASH_30_DOT_EXTRA_CARE, WASH_40_DOT_CARE, WASH_40DOT_EXTRA_CARE,
            WASH_50_DOT_CARE, WASH_60_DOT_CARE, WASH_90_DOT_CARE -> HeaderNameSymbolGuide.WASHING
            BLEACH, BLEACH_NON_CHLORINE, BLEACH_NOT_ALLOWED, BLEACH_NOT_ALLOWED_OLD -> HeaderNameSymbolGuide.BLEACHING
            DRY, DRY_40, DRY_60, DRY_80, DRY_NO_HEAT, DRY_40_CARE, DRY_40_EXTRA_CARE, DRY_60_CARE,
            DRY_NOT_ALLOWED -> HeaderNameSymbolGuide.TUMBLE_DRYING
            DRY_LINE, DRY_SHADE, DRY_LINE_SHADE, DRY_DRIP, DRY_DRIP_SHADE, DRY_FLAT, DRY_FLAT_SHADE, DRY_VERTICAL_LINE -> HeaderNameSymbolGuide.DRYING
            IRON, IRON_110, IRON_150, IRON_200, IRON_NOT_ALLOWED,
            IRON_STEAM_NOT_ALLOWED -> HeaderNameSymbolGuide.IRONING
            DRY_CLEAN, DRY_CLEAN_A, DRY_CLEAN_P, DRY_CLEAN_P2, DRY_CLEAN_F, DRY_CLEAN_F2,
            DRY_CLEAN_SHORT, DRY_CLEAN_LOW_HEAT, DRY_CLEAN_LOW_MOISTURE, DRY_CLEAN_NO_STEAM,
            DRY_CLEAN_NOT_ALLOWED -> HeaderNameSymbolGuide.DRY_CLEANING
            DRY_CLEAN_W, DRY_CLEAN_W2, DRY_CLEAN_W2_LINE, DRY_WET_CLEAN_NOT_ALLOWED -> HeaderNameSymbolGuide.WET_CLEANING
            WRINGING -> HeaderNameSymbolGuide.WRINGING
        }
    }

    fun toSymbolForWashing(context: Context): SymbolGuideItem.SymbolForWashing {
        return SymbolGuideItem.SymbolForWashing(getResIdIcon(), getDescription(context))

    }
}



