package com.blabla.dontruinyourlaundry.entity

import com.blabla.dontruinyourlaundry.data.SymbolForWashing

data class SymbolGuide(
    val headName: HeadNameSymbolGuide,
    val symbolsByCategory: List<SymbolForWashing>
) {
}

enum class HeadNameSymbolGuide(val nameId: String) {
    WASHING("Стирка"),
    BLEACHING("Отбеливание"),
    DRYING("Сушка"),
    IRONING("Глажка"),
    DRYCLEANING("Сухая чистка"),
    WRINGLING("Скручивание")

}