package com.blabla.dontruinyourlaundry.data

import com.blabla.dontruinyourlaundry.R

object GroupOfSymbols {

    fun getSymbolWashAlowed(): List<SymbolForWashing>{
        return listOf(
            SymbolForWashing(R.drawable.wash, "Разрешена машинная стирка")
        )
    }
}