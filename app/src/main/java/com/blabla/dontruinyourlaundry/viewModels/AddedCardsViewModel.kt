package com.blabla.dontruinyourlaundry.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.roomStuff.Card
import com.blabla.dontruinyourlaundry.roomStuff.CardsDao
import com.blabla.dontruinyourlaundry.data.ListOfSymbolsForDataBase
import com.blabla.dontruinyourlaundry.data.SymbolForWashing
import com.blabla.dontruinyourlaundry.entity.Category
import com.blabla.dontruinyourlaundry.entity.CategoryEnum
import kotlinx.coroutines.launch

class AddedCardsViewModel(private val cardsDao: CardsDao) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        Log.d("test", "ViewModel -  onCleared")
    }

    //private val addSymbol = R.drawable.plus
    private val _listOfSymbols = MutableLiveData<MutableList<SymbolForWashing>>()
    val listOfSymbols: LiveData<MutableList<SymbolForWashing>> = _listOfSymbols

//    fun deleteInListSymbolAdd() {
//        if (listOfSymbols.value != null) {
//            listOfSymbols.value!!.removeAt(listOfSymbols.value!!.size - 1)
//        }
//
//    }

    fun updateCard(card: Card) {
        viewModelScope.launch {
                cardsDao.update(card)
        }
    }

    fun updateCard(
        cardId: Long,
        cardName: String,
        cardPicture: String,
        cardSymbols: ListOfSymbolsForDataBase,
        cardCategory: CategoryEnum
    ) {
        val updatedCard = Card(cardId, cardName, cardPicture, cardSymbols, cardCategory)
        updateCard(updatedCard)
    }


    private val _uri = MutableLiveData<Uri>()
    val uri: LiveData<Uri> = _uri

    fun updateUri(newUri: Uri) {
        _uri.value = newUri
    }


    fun addSelectedSymbols(list: List<SymbolForWashing>) {
        val newList = _listOfSymbols.value.orEmpty().toMutableList()
        //check if old list, that is shows on the screen, has duplicates in list of selected symbols
        list.forEach {
            if (!newList.contains(it)) {
                newList.addAll(0, list)
            }
        }
        _listOfSymbols.value = newList
    }



//    fun deleteSelectedSymbols() {
//        Log.d("test", "list before fun deleteSelectedSymbols() ${_listOfSymbols.value.toString()} ")
//        val list = _listOfSymbols.value.orEmpty().toMutableList()
//        val end = list.size - 2
//        val start = 0
//        for (i in end downTo start) {
//            list.removeAt(i)
//        }
//        _listOfSymbols.value = list
//        Log.d("test", "list after fun deleteSelectedSymbols() ${_listOfSymbols.value.toString()} ")
//    }

    fun addNewCard(card: Card?) {
        viewModelScope.launch {
            if (card != null) {
                cardsDao.insertInDataBase(card)
            }
        }

    }

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    fun getCard(id: Long): LiveData<Card> {
        return cardsDao.getOneCard(id).asLiveData()
    }

}

class AddedCardsFactory(private val cardsDao: CardsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddedCardsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddedCardsViewModel(cardsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}



