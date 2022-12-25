package com.blabla.dontruinyourlaundry.domain.useCases

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide

class ChooseSymbolsToCardUseCase(private val repo: Repository) {

    fun setSelectedSymbols(symbols: List<SymbolGuide.SymbolForWashing>): List<SymbolGuide> {

        val symbolsFalseSelected = symbols.map { symbol ->
            symbol.selected = false
            symbol
        }
        val listWithoutSelectedItems = repo.getSymbolGuideList()
        val listWithSelectedItems = listWithoutSelectedItems.map { item ->
            if (item is SymbolGuide.SymbolForWashing && symbolsFalseSelected.contains(item)) {
                item.copy(selected = true)
            } else {
                item
            }
        }
        return listWithSelectedItems
    }

    fun getSelectedItems(list: MutableList<SymbolGuide>): List<SymbolGuide> {
        return list.filter { it is SymbolGuide.SymbolForWashing && it.selected }
    }

    fun clickItem(
        clickedItem: SymbolGuide.SymbolForWashing,
        list: MutableList<SymbolGuide>
    ): List<SymbolGuide> {
        val newList = list.map { itemInList ->
            if (itemInList is SymbolGuide.SymbolForWashing && itemInList == clickedItem) {
                itemInList.copy(selected = !itemInList.selected)
            } else itemInList
        }
        return newList
    }
}