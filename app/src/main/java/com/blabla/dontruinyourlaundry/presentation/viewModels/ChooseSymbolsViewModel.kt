package com.blabla.dontruinyourlaundry.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide
import com.blabla.dontruinyourlaundry.domain.useCases.ChooseSymbolsToCardUseCase

class ChooseSymbolsViewModel(private val chooseSymbolsToCardUC: ChooseSymbolsToCardUseCase) :
    ViewModel() {

    private val _itemsInSymbolGuide = MutableLiveData<List<SymbolGuide>>()
    val itemsInSymbolGuide: LiveData<List<SymbolGuide>> = _itemsInSymbolGuide

    fun setSelectedSymbols(symbols: List<SymbolGuide.SymbolForWashing>) {
        _itemsInSymbolGuide.value = chooseSymbolsToCardUC.setSelectedSymbols(symbols)
    }

    fun clickItem(clickedItem: SymbolGuide.SymbolForWashing) {
        val list = _itemsInSymbolGuide.value.orEmpty().toMutableList()
        _itemsInSymbolGuide.value = chooseSymbolsToCardUC.clickItem(clickedItem, list)
    }

    fun getSelectedItems(): List<SymbolGuide> {
        val listItemsInSymbolGuide = _itemsInSymbolGuide.value.orEmpty().toMutableList()
        return chooseSymbolsToCardUC.getSelectedItems(listItemsInSymbolGuide)
    }
}
