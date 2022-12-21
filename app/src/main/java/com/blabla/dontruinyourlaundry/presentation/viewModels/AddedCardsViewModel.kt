package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsDao
import com.blabla.dontruinyourlaundry.domain.entity.ListOfSymbolsForDataBase
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.domain.entity.Category
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide
import kotlinx.coroutines.launch

class AddedCardsViewModel(private val cardsDao: CardsDao) : ViewModel() {

    private val _lastCardId = MutableLiveData<Long>()

    private val _ListOfSymbolsDBO = MutableLiveData<List<SymbolForWashingDBO>>()

    private val _listOfSymbols = MutableLiveData<List<SymbolGuide.SymbolForWashing>>()
    val listOfSymbols: LiveData<List<SymbolGuide.SymbolForWashing>> = _listOfSymbols

    private val _nameOfCloth = MutableLiveData<String>()
    val nameOfCloth: LiveData<String> = _nameOfCloth

    private val _card = MutableLiveData<Card?>()
    val card: LiveData<Card?> = _card

    private val _cardId = MutableLiveData<Long>()

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    private val _uri = MutableLiveData<Uri>()
    val uri: LiveData<Uri> = _uri

    fun addSymbolsDBO(symbols: List<SymbolForWashingDBO>) {
        _ListOfSymbolsDBO.value = symbols
    }

    fun addCardForEditing(cardId: Long, context: Context) {
        if (cardId > 0 && _cardId.value == null) {
            _cardId.value = cardId
            addCardToViewModel(context)
        }
    }

    fun addCardToViewModel(context: Context) {
        val id = _cardId.value ?: return
        viewModelScope.launch {
            cardsDao.getOneCard(id)
                .collect { card ->
                    _card.value = card
                    addCardInfoToViewModel(card, context)
                }
        }
    }

    fun setName(name: String) {
        if (name == _nameOfCloth.value) return
        else _nameOfCloth.value = name
    }

    fun setCategory(category: Category) {
        _category.value = category
    }

    private fun addCardInfoToViewModel(card: Card, context: Context) {
        _nameOfCloth.value = card.name
        _uri.value = card.picture?.toUri()
        val list = card.listOfSymbols.listOfSymbols.toMutableList()
        _listOfSymbols.value = list.map {
            it.toSymbolForWashing(context)
        }
    }

    fun showDialog(symbol: SymbolGuide.SymbolForWashing, context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val dialog: AlertDialog = builder.setTitle(context.getString(R.string.delete_symbol))
            .setPositiveButton("Ok") { _, _ -> deleteSymbol(symbol) }
            .setIcon(symbol.pictureId)
            .create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(context.resources.getColor(R.color.lilac_700))

    }


    private fun deleteSymbol(symbol: SymbolGuide.SymbolForWashing) {
        val list = _listOfSymbols.value.orEmpty().toMutableList()
        list.remove(symbol)
        _listOfSymbols.value = list
    }

    fun updateUri(newUri: Uri) {
        _uri.value = newUri
    }


    fun addSelectedSymbols(list: List<SymbolGuide.SymbolForWashing>) {
        val listInViewModel = _listOfSymbols.value.orEmpty().toMutableList()
        _listOfSymbols.value = list

    }

    fun addDataToDataBase(card: Card?) {
        addNewCard(card)
        getCardId()
    }

    //REPO
    private fun addNewCard(card: Card?) {
        viewModelScope.launch {
            if (card != null) {
                cardsDao.insertInDataBase(card)
            }
        }

    }

    private fun getCardId() {
        _lastCardId.value = cardsDao.getLastAddedCard().asLiveData().value?.cardId
    }

    private fun addSymbolsToCardsAndSymbols() {
        val symbols = _ListOfSymbolsDBO.value.orEmpty().toMutableList()
        val cardId = _lastCardId.value!!
        viewModelScope.launch {
//            symbols.forEach { symbol ->
//                cardAndSymbol.insertInDataBase(
//                    SymbolsAndCards(symbolId = 0, cardId = cardId, symbol = symbol)
//                )
//            }
        }
    }


    fun saveCardChanges(
        name: String,
        picture: String?,
        listOfSymbols: ListOfSymbolsForDataBase,
    ) {
        val oldCard = _card.value ?: return
        val card = Card(
            cardId = oldCard.cardId,
            name = name,
            picture = picture,
            listOfSymbols = listOfSymbols,
            category = oldCard.category
        )
        updateCard(card)
    }

    //REPO
    private fun updateCard(card: Card) {
        viewModelScope.launch {
            cardsDao.update(card)
        }
    }

}



