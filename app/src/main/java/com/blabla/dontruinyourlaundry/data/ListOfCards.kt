package com.blabla.dontruinyourlaundry.data

import android.content.Context
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.entity.HeadNameSymbolGuide
import com.blabla.dontruinyourlaundry.roomStuff.Card
import com.blabla.dontruinyourlaundry.entity.SymbolGuide

object ListOfCards {

    fun loadListOfSymbolGuide(context: Context): List<SymbolGuide> {
        val listSymbolGuide = mutableListOf<SymbolGuide>()
        HeadNameSymbolGuide.values().map { head ->
            val listSymbols = mutableListOf<SymbolForWashing>()
            SymbolForWashingDBO.values().map { symbolEnum ->
                if (symbolEnum.getTitle() == head) {
                    val resId = symbolEnum.getResIdIcon()
                    val meaning = symbolEnum.getDescription(context)
                    listSymbols.add(SymbolForWashing(resId, meaning))
                }
            }
            listSymbolGuide.add(SymbolGuide(head, listSymbols))
        }
        return listSymbolGuide
    }
}


