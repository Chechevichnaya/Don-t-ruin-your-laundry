package com.blabla.dontruinyourlaundry.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuideItem
import com.blabla.dontruinyourlaundry.domain.useCases.AddSymbolsToCardUseCase

class ChooseSymbolsViewModel(private val chooseSymbolsToCardUC: AddSymbolsToCardUseCase) :
    ViewModel() {

    private val _itemsInSymbolGuide = MutableLiveData<List<SymbolGuideItem>>()
    val itemsInSymbolGuide: LiveData<List<SymbolGuideItem>> = _itemsInSymbolGuide

    fun setSelectedSymbols(symbols: List<SymbolGuideItem.SymbolForWashing>) {
        _itemsInSymbolGuide.value = chooseSymbolsToCardUC.setSelectedSymbols(symbols)
    }

    fun clickItem(clickedItem: SymbolGuideItem.SymbolForWashing) {
        val list = _itemsInSymbolGuide.value.orEmpty().toMutableList()
        _itemsInSymbolGuide.value = chooseSymbolsToCardUC.clickItem(clickedItem, list)
    }

    fun getSelectedItems(): List<SymbolGuideItem> {
        val listItemsInSymbolGuide = _itemsInSymbolGuide.value.orEmpty().toMutableList()
        return chooseSymbolsToCardUC.getSelectedItems(listItemsInSymbolGuide)
    }
}
