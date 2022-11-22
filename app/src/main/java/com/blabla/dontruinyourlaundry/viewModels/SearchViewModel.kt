package com.blabla.dontruinyourlaundry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.blabla.dontruinyourlaundry.roomStuff.Card
import com.blabla.dontruinyourlaundry.roomStuff.CardsDao

class SearchViewModel(private val cardsDao: CardsDao): ViewModel() {

    fun getCardsByName(name:String):LiveData<List<Card>>{
        return cardsDao.getCardsByName(name).asLiveData()
    }

    fun getAllNames():LiveData<List<String>>{
        return cardsDao.getAllNames().asLiveData()
    }
}

class SearchFactory(private val cardsDao: CardsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(cardsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

