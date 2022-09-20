package com.blabla.dontruinyourlaundry.data

import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.RoomStuff.Card
import com.blabla.dontruinyourlaundry.RoomStuff.CardsDao
import com.blabla.dontruinyourlaundry.RoomStuff.CardsDataBase
import com.blabla.dontruinyourlaundry.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AddedCardsViewModel(private val cardsDao: CardsDao) : ViewModel() {


    fun getAllCards():LiveData<List<Card>> = cardsDao.getAllCards()
   // fun allCardsByCategory(): LiveData<List<Card>> = cardsDao.getAllClothCards(_category.value!!)

    fun oneCard(name: String): LiveData<Card> = cardsDao.getOneCard(name)

    fun addNewCard(){
        viewModelScope.launch {
           // val card = Card(_nameCloth.value!!, "", _listOfSymbols.value!!, _category.value!! )
          //  cardsDao.insertInDataBase(card)
        }

    }

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
    fun setCategory(typeOfCategory: Category) {
        _category.value = typeOfCategory
    }
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

    private val _listOfSymbols = MutableLiveData<MutableList<SymbolForWashing>>()
    val listOfSymbols: LiveData<MutableList<SymbolForWashing>> = _listOfSymbols

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

class AddedCardsFactory(private val cardsDao: CardsDao):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddedCardsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddedCardsViewModel(cardsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}



