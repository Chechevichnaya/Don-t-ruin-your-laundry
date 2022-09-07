package com.blabla.dontruinyourlaundry.data

import android.graphics.drawable.Drawable
import android.service.autofill.UserData
import androidx.constraintlayout.widget.ConstraintAttribute
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blabla.dontruinyourlaundry.entity.Category

class AddedCardsViewModel : ViewModel() {

    private val _listOfCards: MutableLiveData<MutableList<CardInfo>> = MutableLiveData()
    val listOfCards: LiveData<MutableList<CardInfo>> = _listOfCards
    private val _newCard: MutableLiveData<CardInfo> = MutableLiveData()
    val newCard: LiveData<CardInfo> = _newCard
    private val _cardsExist: MutableLiveData<Boolean> = MutableLiveData()
    val cardsExist: LiveData<Boolean> = _cardsExist

    init {
        _listOfCards.value = ListOfCards.loadListOfCards()
    }

    fun updateListOfCards(card: CardInfo) {
        _listOfCards.value = mutableListOf(card)

    }

    fun setCategory(typeOfCategory: Category) {
        _category.value = typeOfCategory
    }

    fun setName(nameOfCloth: String) {
        _nameCloth.value = nameOfCloth
    }

    fun setListOfAddedSymbols(listOfAddedSymbols: MutableList<SymbolForWashing>) {
        _listOfSymbols.value = listOfAddedSymbols
    }

    fun setListOfSelectedSymbols(list: MutableList<SymbolForWashing>){
        _listOfSymbols.value = list
    }

    fun cardAreHere(answer:Boolean){
        _cardsExist.value = answer
    }

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