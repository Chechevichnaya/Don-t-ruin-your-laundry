package com.blabla.dontruinyourlaundry.entity

import android.content.Context
import android.os.Parcelable
import com.blabla.dontruinyourlaundry.R
import kotlinx.parcelize.Parcelize


@Parcelize
data class Category(
    val imageResId: Int,
    val nameId: String,
) : Parcelable {

    fun toCategoryDBO(context: Context): CategoryEnum? {
        return CategoryEnum.values().find { enumItem ->
            enumItem.getName(context) == nameId
        }
    }

}

enum class CategoryEnum {
    CLOTH,
    BAD_SHEETS,
    BATH,
    KITCHEN,
    REST;

    fun getResIcon(): Int = when (this) {
        CLOTH -> R.drawable.pyjamas
        BAD_SHEETS -> R.drawable.single_bed
        BATH -> R.drawable.kitchen
        KITCHEN -> R.drawable.kitchen
        REST -> R.drawable.blanket
    }

    fun getName(context: Context): String = when (this) {
        CLOTH -> context.getString(R.string.clothing)
        BAD_SHEETS -> context.getString(R.string.bed_sheets)
        BATH -> context.getString(R.string.bath_stuf)
        KITCHEN -> context.getString(R.string.kitchen_stuf)
        REST -> context.getString(R.string.the_rest)
    }

    fun getNameWithoutContext(): String = when (this) {
        CLOTH -> "Одежда"
        BAD_SHEETS -> "Постельное белье"
        BATH -> "Вещи из ванной"
        KITCHEN -> "Вещи из кухни"
        REST -> "Остальное"
    }

    fun toCategory(context: Context): Category {
        return Category(getResIcon(), getName(context))
    }
}









