package com.blabla.dontruinyourlaundry.viewModels

import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.roomStuff.Card
import com.blabla.dontruinyourlaundry.roomStuff.CardsDao
import com.blabla.dontruinyourlaundry.entity.Category
import com.blabla.dontruinyourlaundry.entity.CategoryEnum

class ClothingCardsViewModel(private val cardsDao: CardsDao) : ViewModel() {

    fun allCardsByCategory(category: CategoryEnum): LiveData<List<Card>> {
        return cardsDao.getCardsByCategory(category).asLiveData()
    }
}

class ClothingCardsFactory(private val cardsDao: CardsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClothingCardsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClothingCardsViewModel(cardsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}