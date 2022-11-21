package com.blabla.dontruinyourlaundry.viewModels

import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.RoomStuff.CardsDao
import kotlinx.coroutines.launch

class CardDetailViewModel(private val cardsDao: CardsDao) : ViewModel() {

    fun deleteCard(card: Card) {
        viewModelScope.launch {
            cardsDao.deleteCard(card)
        }
    }

    fun getCard(id: Long): LiveData<Card> {
        return cardsDao.getOneCard(id).asLiveData()
    }


}

class CardDetailFactory(private val cardsDao: CardsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardDetailViewModel(cardsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}