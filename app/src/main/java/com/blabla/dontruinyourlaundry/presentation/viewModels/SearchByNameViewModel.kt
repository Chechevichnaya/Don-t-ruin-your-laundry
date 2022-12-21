package com.blabla.dontruinyourlaundry.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsDao

class SearchByNameViewModel(private val cardsDao: CardsDao): ViewModel() {

    //REPO
    fun getCardsByName(name:String):LiveData<List<Card>>{
        return cardsDao.getCardsByName(name).asLiveData()
    }

    //REPO
    fun getAllNames():LiveData<List<String>>{
        return cardsDao.getAllNames().asLiveData()
    }
}

//class SearchFactory(private val cardsDao: CardsDao) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return SearchViewModel(cardsDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}

