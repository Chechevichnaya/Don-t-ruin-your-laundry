package com.blabla.dontruinyourlaundry.data

import android.content.Context
import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.blabla.dontruinyourlaundry.R

object ListOfCards {
    fun loadListOfCards(): MutableList<Cardinfo> {
        return mutableListOf()
    }

    fun loadListOfAddedSymbols(): MutableList<SymbolForWashing> {
        val addSymbol = R.drawable.ic_baseline_add_24
        return mutableListOf(SymbolForWashing(addSymbol, "Добавить символы для стирки"))

    }


    fun loadListOfSymbolsForWashingCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(R.drawable.wash, "R.string.wash_hand"),
            SymbolForWashing(
                R.drawable.wh_washing_hand,
                "Стирать только руками при температуре воды до 40 °C. Не тереть, отжимать аккуратно, без перекручивания"
            ),
            SymbolForWashing(R.drawable.wh_washing_not_allowed, ""),
            SymbolForWashing(R.drawable.wh_washing_30deg, "Машинная стирка, нормальный режим."),
            SymbolForWashing(R.drawable.wh_washing_40deg, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_washing_50deg, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_60deg, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_70deg, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_90deg, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_30deg_alt, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_40deg_alt, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_50deg_alt, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_60deg_alt, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_70deg_alt, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_95deg_alt, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_30deg_permanent_press, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_30deg_extra_care, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_40deg_permanent_press, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_40deg_extra_care, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_50deg_permanent_press, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_60deg_permanent_press, "Только ручная стирка"),
            SymbolForWashing(R.drawable.wh_washing_95deg_permanent_press, "Только ручная стирка"),
            SymbolForWashing(
                R.drawable.wh_washing_30deg_permanent_press_alt,
                "Только ручная стирка"
            ),
            SymbolForWashing(R.drawable.wh_washing_30deg_extra_care_alt, "Только ручная стирка"),
            SymbolForWashing(
                R.drawable.wh_washing_40deg_permanent_press_alt,
                "Только ручная стирка"
            ),
            SymbolForWashing(R.drawable.wh_washing_40deg_extra_care_alt, "Только ручная стирка"),
            SymbolForWashing(
                R.drawable.wh_washing_50deg_permanent_press_alt,
                "Только ручная стирка"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_60deg_permanent_press_alt,
                "Только ручная стирка"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_95deg_permanent_press_alt,
                "Только ручная стирка"
            )
        )
    }

    fun loadListOfSymbolsForBleachingCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(R.drawable.wh_bleaching, ""),
            SymbolForWashing(R.drawable.wh_bleaching_non_chlorine, ""),
            SymbolForWashing(R.drawable.wh_bleaching_not_allowed, "")
        )
    }

    fun loadListOfSymbolsForDryingCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(R.drawable.wh_drying_tumble, "Машинная стирка, нормальный режим."),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_low_heat,
                "Машинная стирка, нормальный режим."
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_medium_heat,
                "Машинная стирка, нормальный режим."
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_high_heat,
                "Машинная стирка, нормальный режим."
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_no_heat,
                "Машинная стирка, нормальный режим."
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_low_heat_permanent_press,
                "Машинная стирка, нормальный режим."
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_low_heat_extra_care,
                "Машинная стирка, нормальный режим."
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_medium_heat_permanent_press,
                "Машинная стирка, нормальный режим."
            ),
            SymbolForWashing(R.drawable.wh_drying_line_dry, "Машинная стирка, нормальный режим."),
            SymbolForWashing(R.drawable.wh_drying_dry_shade, "Машинная стирка, нормальный режим."),
            SymbolForWashing(
                R.drawable.wh_drying_line_dry_shade,
                "Машинная стирка, нормальный режим."
            ),
            SymbolForWashing(R.drawable.wh_drying_drip_dry, "Машинная стирка, нормальный режим."),
            SymbolForWashing(
                R.drawable.wh_drying_drip_dry_shade,
                "Машинная стирка, нормальный режим."
            ),
            SymbolForWashing(R.drawable.wh_drying_flat_dry, "Машинная стирка, нормальный режим."),
            SymbolForWashing(R.drawable.wh_drying_flat_dry_shade, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drying_tumble_not_allowed, "Стирка запрещена")

        )
    }

    fun loadListOfSymbolsForIroningCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(R.drawable.wh_ironing, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_ironing_low, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_ironing_medium, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_ironing_high, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_ironing_not_allowed, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_ironing_steam_not_allowed, "Стирка запрещена")
        )
    }

    fun loadListOfSymbolsForDryCleaningCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(R.drawable.wh_drycleaning_a, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_p, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_p_2, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_f, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_f_2, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_w, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_w_2, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_w_3, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_short_cycle, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_low_heat, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_reduced_moisture, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_no_steam, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_not_allowed, "Стирка запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_wetclean_not_allowed, "Стирка запрещена")
        )
    }

    fun loadListOfSymbolsForTwistingCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(R.drawable.wh_wringing_not_allowed, "Стирка запрещена")
        )
    }


    fun loadListOfSymbolGuide(): List<SymbolGuide> {
        return listOf(
            SymbolGuide("Стирка", loadListOfSymbolsForWashingCategory()),
            SymbolGuide("Отбеливание", loadListOfSymbolsForBleachingCategory()),
            SymbolGuide("Сушка", loadListOfSymbolsForDryingCategory()),
            SymbolGuide("Глажка", loadListOfSymbolsForIroningCategory()),
            SymbolGuide("Сухая чистка", loadListOfSymbolsForDryCleaningCategory()),
            SymbolGuide("Скручивание", loadListOfSymbolsForTwistingCategory())

        )

    }
}
//
//enum class SymbolCategory(
//    val nameResId: Int
//) {
//    WASHING(3),
//    DRYING(5)
//
//}
//
//enum class Symbol(
//    val pictureId: Int,
//    val meaningOfSymbol: Int,
//    val category: SymbolCategory
//) {
//    FIRST_TYPE(3, 4, SymbolCategory.WASHING),
//    SECOND_TYPE(3, 4, SymbolCategory.WASHING),
//    SECOND_TYPE3(3, 4, SymbolCategory.DRYING),
//    SECOND_TYPE4(3, 4, SymbolCategory.DRYING),
//    SECOND_TYPE5(3, 4, SymbolCategory.DRYING),
//}
