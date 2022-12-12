package com.blabla.dontruinyourlaundry.viewModels

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.roomStuff.Card
import com.blabla.dontruinyourlaundry.roomStuff.CardsDao
import com.blabla.dontruinyourlaundry.data.ListOfSymbolsForDataBase
import com.blabla.dontruinyourlaundry.entity.Category
import com.blabla.dontruinyourlaundry.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.entity.SymbolGuide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class AddedCardsViewModel(private val cardsDao: CardsDao) : ViewModel() {


    private val _listOfSymbols = MutableLiveData<List<SymbolGuide.SymbolForWashing>>()
    val listOfSymbols: LiveData<List<SymbolGuide.SymbolForWashing>> = _listOfSymbols

    private val _nameOfCloth = MutableLiveData<String>()
    val nameOfCloth: LiveData<String> = _nameOfCloth

    private val _card = MutableLiveData<Card?>()
    val card: LiveData<Card?> = _card

    private val _cardId = MutableLiveData<Long>()
    val cardId: LiveData<Long> = _cardId

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    private val _uri = MutableLiveData<Uri>()
    val uri: LiveData<Uri> = _uri

    fun addCardId(cardId: Long) {
        if (cardId > 0) _cardId.value = cardId
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

    fun addSymbolsFromOldCardToViewModel(card: Card, context: Context) {
        val list = card.listOfSymbols.listOfSymbols.toMutableList()
        _listOfSymbols.value = list.map { it.toSymbolForWashing(context) }
    }

    fun updateUri(newUri: Uri) {
        _uri.value = newUri
    }


    fun addSelectedSymbols(list: List<SymbolGuide.SymbolForWashing>) {
        val newList = _listOfSymbols.value.orEmpty().toMutableList()
        newList.addAll(list)
        _listOfSymbols.value = newList
    }


    fun addNewCard(card: Card?) {
        viewModelScope.launch {
            if (card != null) {
                cardsDao.insertInDataBase(card)
            }
        }

    }


    fun saveCardChanges(
        name: String,
        picture: String?,
        listOfSymbols: ListOfSymbolsForDataBase,
    ) {
        val oldCard = _card.value ?: return
        val card = Card(
            id = oldCard.id,
            name = name,
            picture = picture,
            listOfSymbols = listOfSymbols,
            category = oldCard.category
        )
        viewModelScope.launch {
            cardsDao.update(card)
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
}



