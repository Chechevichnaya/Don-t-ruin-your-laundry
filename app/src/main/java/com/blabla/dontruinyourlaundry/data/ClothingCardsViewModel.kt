package com.blabla.dontruinyourlaundry.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.RoomStuff.CardsDao
import com.blabla.dontruinyourlaundry.entity.Category

class ClothingCardsViewModel(private val cardsDao: CardsDao) : ViewModel() {

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    fun checkTableIsEmpty(): Boolean {
        val listOfCards = allCardsByCategory()?.value
        return listOfCards == null
    }

    fun allCardsByCategory(): LiveData<List<Card>>? {
         return _category.value?.let { cardsDao.getAllClothCardsByCategory(it) }
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