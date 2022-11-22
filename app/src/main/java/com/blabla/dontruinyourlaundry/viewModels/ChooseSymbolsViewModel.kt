package com.blabla.dontruinyourlaundry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blabla.dontruinyourlaundry.data.SymbolForWashing

class ChooseSymbolsViewModel: ViewModel() {

    private val _selectedSymbols = MutableLiveData<List<SymbolForWashing>>()
    val selectedSymbols: LiveData<List<SymbolForWashing>> = _selectedSymbols
    private val _amountOfSymbols = MutableLiveData(0)
    val amountOfSymbols: LiveData<Int> = _amountOfSymbols

    fun setSelectedSymbols(list: List<SymbolForWashing>){
        _selectedSymbols.value = list
    }






    fun addAmount(){
        _amountOfSymbols.value = _amountOfSymbols.value?.plus(1)
    }

}