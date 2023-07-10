package com.blabla.dontruinyourlaundry.domain.useCases

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuideItem

class AddSymbolsToCardUseCase(private val repo: Repository) {
    fun setSelectedSymbols(symbols: List<SymbolGuideItem.SymbolForWashing>): List<SymbolGuideItem> {

        val symbolsFalseSelected = symbols.map { symbol ->
            symbol.selected = false
            symbol
        }
        val listWithoutSelectedItems = repo.getSymbolGuideList()
        val listWithSelectedItems = listWithoutSelectedItems.map { item ->
            if (item is SymbolGuideItem.SymbolForWashing && symbolsFalseSelected.contains(item)) {
                item.copy(selected = true)
            } else {
                item
            }
        }
        return listWithSelectedItems
    }

    fun getSelectedItems(list: MutableList<SymbolGuideItem>): List<SymbolGuideItem> {
        return list.filter { it is SymbolGuideItem.SymbolForWashing && it.selected }
    }

    fun clickItem(
        clickedItem: SymbolGuideItem.SymbolForWashing,
        list: MutableList<SymbolGuideItem>
    ): List<SymbolGuideItem> {
        val newList = list.map { itemInList ->
            if (itemInList is SymbolGuideItem.SymbolForWashing && itemInList == clickedItem) {
                itemInList.copy(selected = !itemInList.selected)
            } else itemInList
        }
        return newList
    }
}