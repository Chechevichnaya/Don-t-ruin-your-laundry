package com.blabla.dontruinyourlaundry.data

import android.content.Context
import android.util.Log
import com.blabla.dontruinyourlaundry.entity.HeadNameSymbolGuide
import com.blabla.dontruinyourlaundry.entity.SymbolGuide

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


