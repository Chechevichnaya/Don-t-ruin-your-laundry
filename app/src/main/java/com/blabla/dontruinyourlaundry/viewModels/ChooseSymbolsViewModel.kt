package com.blabla.dontruinyourlaundry.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blabla.dontruinyourlaundry.data.ListOfCards
import com.blabla.dontruinyourlaundry.entity.SymbolGuide

class ChooseSymbolsViewModel : ViewModel() {

    private val _itemsInSymbolGuide = MutableLiveData<List<SymbolGuide>>()
    val itemsInSymbolGuide: LiveData<List<SymbolGuide>> = _itemsInSymbolGuide

    fun setSelectedSymbols(symbols: List<SymbolGuide.SymbolForWashing>) {
        Log.d("CHECK", "BEFORE _itemsInSymbolGuide.value = ${ _itemsInSymbolGuide.value}")
        val list = _itemsInSymbolGuide.value.orEmpty()
        _itemsInSymbolGuide.value = list.map { item ->
            if (item is SymbolGuide.SymbolForWashing && symbols.contains(item)) {
                Log.d("CHECK", "INSIDE YES")
                item.copy(selected = true)

            }
            else
                Log.d("CHECK", "INSIDE NO")
                item
        }
        Log.d("CHECK", "AFTER _itemsInSymbolGuide.value = ${ _itemsInSymbolGuide.value}")
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
        val selectedList = listMutable.filter { it is SymbolGuide.SymbolForWashing && it.selected }
        return selectedList
    }
}
