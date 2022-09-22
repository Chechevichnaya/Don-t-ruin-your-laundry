package com.blabla.dontruinyourlaundry.data

import android.os.health.SystemHealthManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChooseSymbolsViewModel: ViewModel() {

    private val _selectedSymbols = MutableLiveData<List<SymbolForWashing>>()
    val selectedSymbols: LiveData<List<SymbolForWashing>> = _selectedSymbols

    fun setSelectedSymbols(list: List<SymbolForWashing>){
        _selectedSymbols.value = list
    }
}