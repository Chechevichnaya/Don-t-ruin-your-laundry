package com.blabla.dontruinyourlaundry.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.entity.SymbolGuide
import com.blabla.dontruinyourlaundry.roomStuff.Card
import com.blabla.dontruinyourlaundry.roomStuff.CardsDao
import kotlinx.coroutines.launch

class CardDetailViewModel(private val cardsDao: CardsDao) : ViewModel() {

    private val _listOfSymbols = MutableLiveData<List<SymbolGuide.SymbolForWashing>>()
    val listOfSymbols: LiveData<List<SymbolGuide.SymbolForWashing>> = _listOfSymbols

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _picture = MutableLiveData<String?>()
    val picture: LiveData<String?> = _picture

    private val _category = MutableLiveData<CategoryEnum>()
    val category: LiveData<CategoryEnum> = _category

    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card


    fun deleteCard(card: Card) {
        viewModelScope.launch {
            cardsDao.deleteCard(card)
        }
    }

    fun addInfoToViewModel(card: Card, context: Context) {
        val list = card.listOfSymbols.listOfSymbols
        _listOfSymbols.value =
            list.map { item ->
                item.toSymbolForWashing(context)
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