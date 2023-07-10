package com.blabla.dontruinyourlaundry.domain.entity

import android.content.Context

object SymbolGuideItemProvider {

    fun getSymbolGuideItems(context: Context): List<SymbolGuideItem> {
        val listResult = mutableListOf<SymbolGuideItem>()
        HeaderNameSymbolGuide.values().map { header ->
            listResult.add(SymbolGuideItem.HeaderName(header))
            SymbolForWashingDBO.values().map { symbol ->
                if (symbol.getTitle() == header) {
                    val resId = symbol.getResIdIcon()
                    val meaning = symbol.getDescription(context)
                    listResult.add(SymbolGuideItem.SymbolForWashing(resId, meaning))
                }
            }
        }
        return listResult
    }
}


