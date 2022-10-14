package com.blabla.dontruinyourlaundry.data

import android.util.Log
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.RoomStuff.CardsDao
import com.blabla.dontruinyourlaundry.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class ClothingCardsViewModel(private val cardsDao: CardsDao) : ViewModel() {

    fun checkDBIsEmpty(category: Category): Boolean {
        val listOfCards = allCardsByCategory(category)
        return listOfCards.value == null
    }

//    fun checkDBIsEmpty1(category: Category): Boolean {
//        var answer = true
//        viewModelScope.launch {
//            answer =  cardsDao.isEmpty(category)
//        }
//        return answer
//    }


    fun allCardsByCategory(category: Category): LiveData<List<Card>> {
        return cardsDao.getAllClothCardsByCategory(category).asLiveData()
    }

    fun testsome(value: String?) {
        if (value == null) return

        value.toList()
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