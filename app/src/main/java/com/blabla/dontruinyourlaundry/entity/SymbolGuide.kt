package com.blabla.dontruinyourlaundry.entity

import com.blabla.dontruinyourlaundry.data.SymbolForWashing

data class SymbolGuide(
    val headName: String,
    val symbolsByCategory: List<SymbolForWashing>
) {
}