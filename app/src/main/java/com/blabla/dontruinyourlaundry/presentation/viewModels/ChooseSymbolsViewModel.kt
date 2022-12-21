package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blabla.dontruinyourlaundry.domain.entity.ListOfCards
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide

class ChooseSymbolsViewModel : ViewModel() {

    private val _itemsInSymbolGuide = MutableLiveData<List<SymbolGuide>>()
    val itemsInSymbolGuide: LiveData<List<SymbolGuide>> = _itemsInSymbolGuide

    fun setSelectedSymbols(symbols: List<SymbolGuide.SymbolForWashing>) {
        val symbolsFalseSelected = symbols.map { symbol ->
            symbol.selected = false
            symbol
        }
        val list = _itemsInSymbolGuide.value.orEmpty()
        _itemsInSymbolGuide.value = list.map { item ->
            if (item is SymbolGuide.SymbolForWashing && symbolsFalseSelected.contains(item)) {
                item.copy(selected = true)
            } else {
                item
            }
        }
    }


    fun giveContextToViewModel(context: Context) {
        _itemsInSymbolGuide.value = ListOfCards.loadListOfSymbolGuide(context)
    }

    fun onClicked(clickedItem: SymbolGuide.SymbolForWashing) {
        val list = _itemsInSymbolGuide.value ?: return
        val listMutable = list.toMutableList()
        val newList = listMutable.map { itemInList ->
            if (itemInList is SymbolGuide.SymbolForWashing && itemInList == clickedItem) {
                itemInList.copy(selected = !itemInList.selected)
            } else itemInList
        }
        _itemsInSymbolGuide.value = newList
    }

    fun getSelectedItems(): List<SymbolGuide> {
        val list = _itemsInSymbolGuide.value
        val listMutable = list?.toMutableList().orEmpty()
        return listMutable.filter { it is SymbolGuide.SymbolForWashing && it.selected }
    }
}
