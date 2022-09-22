package com.blabla.dontruinyourlaundry.data

import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.RoomStuff.CardsDao
import com.blabla.dontruinyourlaundry.RoomStuff.CardsDataBase
import com.blabla.dontruinyourlaundry.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Collections.addAll

class AddedCardsViewModel(private val cardsDao: CardsDao) : ViewModel() {

    private val addSymbol = R.drawable.plus
    private val _listOfSymbols =
        MutableLiveData(mutableListOf(SymbolForWashing(addSymbol, "Добавить символы для стирки")))
    val listOfSymbols: LiveData<MutableList<SymbolForWashing>> = _listOfSymbols


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

    fun deleteSelectedSymbols() {
        val list = _listOfSymbols.value.orEmpty().toMutableList()
        val end = list.size - 2
        val start = 0
        for (i in end downTo start) {
            list.removeAt(i)
        }
    }

//    fun addNewCard(card: Card) {
//        viewModelScope.launch {
//            cardsDao.insertInDataBase(card)
//        }
//
//    }

//
//    private val _listOfCards: MutableLiveData<MutableList<Card>> = MutableLiveData()
//    val listOfCards: LiveData<MutableList<Card>> = _listOfCards
//    private val _newCard: MutableLiveData<Card> = MutableLiveData()
//    val newCard: LiveData<Card> = _newCard
//    private val _cardsExist: MutableLiveData<Boolean> = MutableLiveData()
//    val cardsExist: LiveData<Boolean> = _cardsExist
//
//    init {
//        _listOfCards.value = ListOfCards.loadListOfCards()
//    }
//
//    fun updateListOfCards(card: Card) {
//        _listOfCards.value = mutableListOf(card)
//
//    }
//

    //
//    fun setName(nameOfCloth: String) {
//        _nameCloth.value = nameOfCloth
//    }
//
//    fun setListOfAddedSymbols(listOfAddedSymbols: MutableList<SymbolForWashing>) {
//        _listOfSymbols.value = listOfAddedSymbols
//    }
//
//    fun setListOfSelectedSymbols(list: MutableList<SymbolForWashing>){
//        _listOfSymbols.value = list
//    }
//
//    fun cardAreHere(answer:Boolean){
//        _cardsExist.value = answer
//    }
//
    private val _nameCloth = MutableLiveData<String>()
    val nameCloth: LiveData<String> = _nameCloth


    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category


//    var userList : MutableLiveData<List<User>> = MutableLiveData()
//
//    //инициализируем список и заполняем его данными пользователей
//    init {
//        userList.value = UserData.getUsers()
//    }
//
//    fun getListUsers() = userList
//
//    //для обновления списка передаем второй список пользователей
//    fun updateListUsers() {
//        userList.value = UserData.getAnotherUsers()
//    }

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



