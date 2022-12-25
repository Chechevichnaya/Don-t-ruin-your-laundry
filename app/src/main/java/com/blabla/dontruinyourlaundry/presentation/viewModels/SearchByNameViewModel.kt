package com.blabla.dontruinyourlaundry.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.domain.useCases.SearchByNameUseCase

class SearchByNameViewModel(private val searchByNameUC: SearchByNameUseCase): ViewModel() {

    fun getCardsByName(name:String):LiveData<List<Card>>{
        return searchByNameUC.getCardsByName(name).asLiveData()
    }

    fun getAllNames():LiveData<List<String>>{
        return searchByNameUC.getAllNames().asLiveData()
    }
}


