package com.blabla.dontruinyourlaundry.domain.entity

import android.content.Context
import android.os.Parcelable
import com.blabla.dontruinyourlaundry.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListOfSymbols(val list: List<SymbolGuide.SymbolForWashing>) : Parcelable

@Parcelize
enum class SymbolForWashingDBO : Parcelable {
    WASH,
    WASHNOTALLOWED,
    WASHHAND,
    WASHHAND30,
    WASHHAND40,
    WASH30,
    WASH40,
    WASH50,
    WASH60,
    WASH70,
    WASH90,
    WASH30CARE,
    WASH30EXTRACARE,
    WASH40CARE,
    WASH40EXTRACARE,
    WASH50CARE,
    WASH60CARE,
    WASH90CARE,
    WASH30DOT,
    WASH40DOT,
    WASH50DOT,
    WASH60DOT,
    WASH70DOT,
    WASH90DOT,
    WASH30DOTCARE,
    WASH30DOTEXTRACARE,
    WASH40DOTCARE,
    WASH40DOTEXTRACARE,
    WASH50DOTCARE,
    WASH60DOTCARE,
    WASH90DOTCARE,
    BLEACH,
    BLEACHNONCHLORINE,
    BLEACHNOTALLOWED,
    BLEACH_NOT_ALLOWED_OLD,
    DRY,
    DRY40,
    DRY60,
    DRY80,
    DRYNOHEAT,
    DRY40CARE,
    DRY40EXTRACARE,
    DRY60CARE,
    DRYLINE,
    DRY_VERTICAL_LINE,
    DRYSHADE,
    DRYLINESHADE,
    DRYDRIP,
    DRYDRIPSHADE,
    DRYFLAT,
    DRYFLASTHADE,
    DRYNOTALLOWED,
    IRON,
    IRON110,
    IRON150,
    IRON200,
    IRONNOTALLOWED,
    IRONSTEAMNOTALLOWED,
    DRYCLEAN,
    DRYCLEANA,
    DRYCLEANP,
    DRY_CLEAN_P2,
    DRYCLEANF,
    DRY_CLEAN_F2,
    DRY_CLEAN_W,
    DRY_CLEAN_W2,
    DRY_CLEAN_W2LINE,
    DRYCLEANSHORT,
    DRYCLEANLOWHEAT,
    DRYCLEANLOWMOISTURE,
    DRYCLEANNOSTEAM,
    DRYCLEANNOTALLOWED,
    DRY_WET_CLEAM_NOT_ALLOWED,
    WRINGING;

    fun getDescription(context: Context): String {
        return when (this) {
            WASH -> context.getString(R.string.wash)
            WASHNOTALLOWED -> context.getString(R.string.wash_not_allowed)
            WASHHAND -> context.getString(R.string.wash_hand)
            WASHHAND30 -> context.getString(R.string.wash_hand30)
            WASHHAND40 -> context.getString(R.string.wash_hand40)
            WASH30 -> context.getString(R.string.wash30)
            WASH40 -> context.getString(R.string.wash40)
            WASH50 -> context.getString(R.string.wash50)
            WASH60 -> context.getString(R.string.wash60)
            WASH70 -> context.getString(R.string.wash70)
            WASH90 -> context.getString(R.string.wash90)
            WASH30CARE -> context.getString(R.string.wash30_care)
            WASH30EXTRACARE -> context.getString(R.string.wash30_extracare)
            WASH40CARE -> context.getString(R.string.wash40_care)
            WASH40EXTRACARE -> context.getString(R.string.wash40_extracare)
            WASH50CARE -> context.getString(R.string.wash50_care)
            WASH60CARE -> context.getString(R.string.wash60_care)
            WASH90CARE -> context.getString(R.string.wash90_care)
            WASH30DOT -> context.getString(R.string.wash30)
            WASH40DOT -> context.getString(R.string.wash40)
            WASH50DOT -> context.getString(R.string.wash50)
            WASH60DOT -> context.getString(R.string.wash60)
            WASH70DOT -> context.getString(R.string.wash70)
            WASH90DOT -> context.getString(R.string.wash90)
            WASH30DOTCARE -> context.getString(R.string.wash30_care)
            WASH30DOTEXTRACARE -> context.getString(R.string.wash30_extracare)
            WASH40DOTCARE -> context.getString(R.string.wash40_care)
            WASH40DOTEXTRACARE -> context.getString(R.string.wash40_extracare)
            WASH50DOTCARE -> context.getString(R.string.wash50_care)
            WASH60DOTCARE -> context.getString(R.string.wash60_care)
            WASH90DOTCARE -> context.getString(R.string.wash90_care)
            BLEACH -> context.getString(R.string.bleach)
            BLEACHNONCHLORINE -> context.getString(R.string.bleach_non_chlor)
            BLEACHNOTALLOWED -> context.getString(R.string.bleach_not_allowed)
            BLEACH_NOT_ALLOWED_OLD -> context.getString(R.string.bleach_not_allowed)
            DRY -> context.getString(R.string.dry)
            DRY40 -> context.getString(R.string.dry40)
            DRY60 -> context.getString(R.string.dry60)
            DRY80 -> context.getString(R.string.dry80)
            DRYNOHEAT -> context.getString(R.string.dry_no_heat)
            DRY40CARE -> context.getString(R.string.dry40_care)
            DRY40EXTRACARE -> context.getString(R.string.dry40_extracare)
            DRY60CARE -> context.getString(R.string.dry60_care)
            DRYLINE -> context.getString(R.string.dry_line)
            DRYSHADE -> context.getString(R.string.dry_shade)
            DRYLINESHADE -> context.getString(R.string.dry_line_shade)
            DRYDRIP -> context.getString(R.string.dry_drip)
            DRYDRIPSHADE -> context.getString(R.string.dry_drip_shade)
            DRYFLAT -> context.getString(R.string.dry_flat)
            DRYFLASTHADE -> context.getString(R.string.dry_flat_shade)
            DRY_VERTICAL_LINE -> context.getString(R.string.dry_vertical_line)
            DRYNOTALLOWED -> context.getString(R.string.dry_not_alloder)
            IRON -> context.getString(R.string.iron)
            IRON110 -> context.getString(R.string.iron110)
            IRON150 -> context.getString(R.string.iron150)
            IRON200 -> context.getString(R.string.iron200)
            IRONNOTALLOWED -> context.getString(R.string.iron_not_allowed)
            IRONSTEAMNOTALLOWED -> context.getString(R.string.iron_steam_not_allowed)
            DRYCLEAN -> context.getString(R.string.dry_clean)
            DRYCLEANA -> context.getString(R.string.dry_cleana)
            DRYCLEANP -> context.getString(R.string.dry_cleanp)
            DRY_CLEAN_P2 -> context.getString(R.string.dry_cleanp2)
            DRYCLEANF -> context.getString(R.string.dry_cleanf)
            DRY_CLEAN_F2 -> context.getString(R.string.dry_cleanf2)
            DRY_CLEAN_W -> context.getString(R.string.dry_cleanw)
            DRY_CLEAN_W2 -> context.getString(R.string.dry_cleanw2)
            DRY_CLEAN_W2LINE -> context.getString(R.string.dry_cleanw3)
            DRY_WET_CLEAM_NOT_ALLOWED -> context.getString(R.string.dry_cleanwet_not_allowed)
            DRYCLEANSHORT -> context.getString(R.string.dry_clean_short)
            DRYCLEANLOWHEAT -> context.getString(R.string.dry_clean_lowheat)
            DRYCLEANLOWMOISTURE -> context.getString(R.string.dry_clean_low_water)
            DRYCLEANNOSTEAM -> context.getString(R.string.dry_clean_no_steam)
            DRYCLEANNOTALLOWED -> context.getString(R.string.dry_clean_not_allowed)
            WRINGING -> context.getString(R.string.wringing_text)
        }
    }

    fun getResIdIcon(): Int = when (this) {
        WASH -> R.drawable.wash
        WASHNOTALLOWED -> R.drawable.wh_washing_not_allowed
        WASHHAND -> R.drawable.wh_washing_hand
        WASHHAND30 -> R.drawable.wh_washing_hand_30deg
        WASHHAND40 -> R.drawable.wh_washing_hand_40deg
        WASH30 -> R.drawable.wh_washing_30deg
        WASH40 -> R.drawable.wh_washing_40deg
        WASH50 -> R.drawable.wh_washing_50deg
        WASH60 -> R.drawable.wh_washing_60deg
        WASH70 -> R.drawable.wh_washing_70deg
        WASH90 -> R.drawable.wh_washing_90deg
        WASH30CARE -> R.drawable.wh_washing_30deg_permanent_press
        WASH30EXTRACARE -> R.drawable.wh_washing_30deg_extra_care
        WASH40CARE -> R.drawable.wh_washing_40deg_permanent_press
        WASH40EXTRACARE -> R.drawable.wh_washing_40deg_extra_care
        WASH50CARE -> R.drawable.wh_washing_50deg_permanent_press
        WASH60CARE -> R.drawable.wh_washing_60deg_permanent_press
        WASH90CARE -> R.drawable.wh_washing_95deg_permanent_press
        WASH30DOT -> R.drawable.wh_washing_30deg_alt
        WASH40DOT -> R.drawable.wh_washing_40deg_alt
        WASH50DOT -> R.drawable.wh_washing_50deg_alt
        WASH60DOT -> R.drawable.wh_washing_60deg_alt
        WASH70DOT -> R.drawable.wh_washing_70deg_alt
        WASH90DOT -> R.drawable.wh_washing_95deg_alt
        WASH30DOTCARE -> R.drawable.wh_washing_30deg_permanent_press_alt
        WASH30DOTEXTRACARE -> R.drawable.wh_washing_30deg_extra_care_alt
        WASH40DOTCARE -> R.drawable.wh_washing_40deg_permanent_press_alt
        WASH40DOTEXTRACARE -> R.drawable.wh_washing_40deg_extra_care_alt
        WASH50DOTCARE -> R.drawable.wh_washing_50deg_permanent_press_alt
        WASH60DOTCARE -> R.drawable.wh_washing_60deg_permanent_press_alt
        WASH90DOTCARE -> R.drawable.wh_washing_95deg_permanent_press_alt
        BLEACH -> R.drawable.wh_bleaching
        BLEACHNONCHLORINE -> R.drawable.wh_bleaching_non_chlorine
        BLEACHNOTALLOWED -> R.drawable.wh_bleaching_not_allowed_white
        BLEACH_NOT_ALLOWED_OLD -> R.drawable.wh_bleaching_not_allowed
        DRY -> R.drawable.wh_drying_tumble
        DRY40 -> R.drawable.wh_drying_tumble_low_heat
        DRY60 -> R.drawable.wh_drying_tumble_medium_heat
        DRY80 -> R.drawable.wh_drying_tumble_high_heat
        DRYNOHEAT -> R.drawable.wh_drying_tumble_no_heat
        DRY40CARE -> R.drawable.wh_drying_tumble_low_heat_permanent_press
        DRY40EXTRACARE -> R.drawable.wh_drying_tumble_low_heat_extra_care
        DRY60CARE -> R.drawable.wh_drying_tumble_medium_heat_permanent_press
        DRYLINE -> R.drawable.wh_drying_line_dry
        DRYSHADE -> R.drawable.wh_drying_dry_shade
        DRYLINESHADE -> R.drawable.wh_drying_line_dry_shade
        DRYDRIP -> R.drawable.wh_drying_drip_dry
        DRYDRIPSHADE -> R.drawable.wh_drying_drip_dry_shade
        DRYFLAT -> R.drawable.wh_drying_flat_dry
        DRYFLASTHADE -> R.drawable.wh_drying_flat_dry_shade
        DRY_VERTICAL_LINE -> R.drawable.wh_drying_flat_dry2
        DRYNOTALLOWED -> R.drawable.wh_drying_tumble_not_allowed
        IRON -> R.drawable.wh_ironing
        IRON110 -> R.drawable.wh_ironing_low
        IRON150 -> R.drawable.wh_ironing_medium
        IRON200 -> R.drawable.wh_ironing_high
        IRONNOTALLOWED -> R.drawable.wh_ironing_not_allowed
        IRONSTEAMNOTALLOWED -> R.drawable.wh_ironing_steam_not_allowed
        DRYCLEAN -> R.drawable.wh_drycleaning
        DRYCLEANA -> R.drawable.wh_drycleaning_a
        DRYCLEANP -> R.drawable.wh_drycleaning_p
        DRY_CLEAN_P2 -> R.drawable.wh_drycleaning_p_2
        DRYCLEANF -> R.drawable.wh_drycleaning_f
        DRY_CLEAN_F2 -> R.drawable.wh_drycleaning_f_2
        DRY_CLEAN_W -> R.drawable.wh_drycleaning_w
        DRY_CLEAN_W2 -> R.drawable.wh_drycleaning_w_2
        DRY_CLEAN_W2LINE -> R.drawable.wh_drycleaning_w_3
        DRYCLEANSHORT -> R.drawable.wh_drycleaning_short_cycle
        DRYCLEANLOWHEAT -> R.drawable.wh_drycleaning_low_heat
        DRYCLEANLOWMOISTURE -> R.drawable.wh_drycleaning_reduced_moisture
        DRYCLEANNOSTEAM -> R.drawable.wh_drycleaning_no_steam
        DRYCLEANNOTALLOWED -> R.drawable.wh_drycleaning_not_allowed
        DRY_WET_CLEAM_NOT_ALLOWED -> R.drawable.wh_drycleaning_wetclean_not_allowed
        WRINGING -> R.drawable.wh_wringing_not_allowed

    }

    fun getTitle(): HeadNameSymbolGuide {
        return when (this) {
            WASH, WASHNOTALLOWED, WASHHAND, WASHHAND30, WASHHAND40, WASH30, WASH40, WASH50, WASH60,
            WASH70, WASH90, WASH30CARE, WASH30EXTRACARE, WASH40CARE, WASH40EXTRACARE, WASH50CARE,
            WASH60CARE, WASH90CARE, WASH30DOT, WASH40DOT, WASH50DOT, WASH60DOT, WASH70DOT,
            WASH90DOT, WASH30DOTCARE, WASH30DOTEXTRACARE, WASH40DOTCARE, WASH40DOTEXTRACARE,
            WASH50DOTCARE, WASH60DOTCARE, WASH90DOTCARE -> HeadNameSymbolGuide.WASHING
            BLEACH, BLEACHNONCHLORINE, BLEACHNOTALLOWED, BLEACH_NOT_ALLOWED_OLD -> HeadNameSymbolGuide.BLEACHING
            DRY, DRY40, DRY60, DRY80, DRYNOHEAT, DRY40CARE, DRY40EXTRACARE, DRY60CARE,
            DRYNOTALLOWED -> HeadNameSymbolGuide.TUMBLE_DRYING
            DRYLINE, DRYSHADE, DRYLINESHADE, DRYDRIP, DRYDRIPSHADE, DRYFLAT, DRYFLASTHADE, DRY_VERTICAL_LINE -> HeadNameSymbolGuide.DRYING
            IRON, IRON110, IRON150, IRON200, IRONNOTALLOWED,
            IRONSTEAMNOTALLOWED -> HeadNameSymbolGuide.IRONING
            DRYCLEAN, DRYCLEANA, DRYCLEANP, DRY_CLEAN_P2, DRYCLEANF, DRY_CLEAN_F2,
            DRYCLEANSHORT, DRYCLEANLOWHEAT, DRYCLEANLOWMOISTURE, DRYCLEANNOSTEAM,
            DRYCLEANNOTALLOWED -> HeadNameSymbolGuide.DRYCLEANING
            DRY_CLEAN_W, DRY_CLEAN_W2, DRY_CLEAN_W2LINE, DRY_WET_CLEAM_NOT_ALLOWED -> HeadNameSymbolGuide.WETCLEANING
            WRINGING -> HeadNameSymbolGuide.WRINGING
        }
    }

    fun toSymbolForWashing(context: Context): SymbolGuide.SymbolForWashing {
        return SymbolGuide.SymbolForWashing(getResIdIcon(), getDescription(context))

    }
}



