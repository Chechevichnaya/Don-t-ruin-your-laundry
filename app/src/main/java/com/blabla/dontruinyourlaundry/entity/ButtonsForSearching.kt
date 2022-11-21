package com.blabla.dontruinyourlaundry.entity

import com.blabla.dontruinyourlaundry.data.SymbolForWashing

data class ButtonsForSearching(
    val name:String,
//    val listSymbols: List<SymbolForWashing>,
    var selected: Boolean = false // TODO remove!

)
