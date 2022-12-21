package com.blabla.dontruinyourlaundry.presentation.viewModels

import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsDao
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum

class ClothingCardsViewModel(private val cardsDao: CardsDao) : ViewModel() {

    fun allCardsByCategory(category: CategoryEnum): LiveData<List<Card>> {
        return cardsDao.getCardsByCategory(category).asLiveData()
    }
}

//class ClothingCardsFactory(private val cardsDao: CardsDao) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ClothingCardsViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ClothingCardsViewModel(cardsDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}