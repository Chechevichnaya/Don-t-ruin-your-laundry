package com.blabla.dontruinyourlaundry.domain.entity

import android.content.Context

object ListOfCards {

    fun loadListOfSymbolGuide(context: Context): List<SymbolGuide> {
        val listResult = mutableListOf<SymbolGuide>()
        HeadNameSymbolGuide.values().map { head ->
            listResult.add(SymbolGuide.HeadName(head))
            SymbolForWashingDBO.values().map { symbolEnum ->
                if (symbolEnum.getTitle() == head) {
                    val resId = symbolEnum.getResIdIcon()
                    val meaning = symbolEnum.getDescription(context)
                    listResult.add(SymbolGuide.SymbolForWashing(resId, meaning))
                }
            }
        }
        return listResult
    }
}


