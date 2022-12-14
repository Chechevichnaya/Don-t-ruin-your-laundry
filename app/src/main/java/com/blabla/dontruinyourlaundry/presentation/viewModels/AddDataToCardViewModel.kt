package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsAndSymbols
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.domain.entity.Category
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide
import com.blabla.dontruinyourlaundry.domain.useCases.CreateNewCardUseCase
import com.blabla.dontruinyourlaundry.domain.useCases.ChangeOldCardUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddDataToCardViewModel(
    private val addNewCardUC: CreateNewCardUseCase,
    private val updateCardUC: ChangeOldCardUseCase,
    private val context: Context
) : ViewModel() {

//    private val symbolsAddNewSymbols = SymbolGuide.ButtonAddNewSymbol(
//        R.drawable.ic_add,
//        context.getString(R.string.add_more_symbols)
//    )


    private val _listOfSymbols = MutableLiveData<List<SymbolGuide>>()
    val listOfSymbols: LiveData<List<SymbolGuide>> = _listOfSymbols

    private val _nameOfCloth = MutableLiveData<String>()
    val nameOfCloth: LiveData<String> = _nameOfCloth

    private val _card = MutableLiveData<Card?>()
    val card: LiveData<Card?> = _card

    private val _cardId = MutableLiveData<Long>()

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    private val _uri = MutableLiveData<Uri>()
    val uri: LiveData<Uri> = _uri

    fun addCardForEditing(cardId: Long, context: Context) {
        if (cardId > 0 && _cardId.value == null) {
            _cardId.value = cardId
            addCardToViewModel(context)
        }
    }

//    fun checkIfThereIsItemAddNewSymbol(items: List<SymbolGuide>): List<SymbolGuide> {
//        return if (items.isEmpty() || (items.last() is SymbolGuide.ButtonAddNewSymbol).not()) {
//            val itemsList = items.toMutableList()
//            itemsList.add(
//                symbolsAddNewSymbols
//            )
//            itemsList
//        } else items
//    }

//    fun removeSymbolsAddNewSymbols(): List<SymbolGuide> {
//        val list = _listOfSymbols.value.orEmpty().toMutableList()
//        if (list.contains(symbolsAddNewSymbols)) {
//            list.remove(symbolsAddNewSymbols)
//        }
//        return list
//    }

//    fun addItemAddNewSymbol() {
//        val listOfSymbols = _listOfSymbols.value.orEmpty().toMutableList()
//        listOfSymbols.add(
//            symbolsAddNewSymbols
//        )
//        _listOfSymbols.value = listOfSymbols
//    }

    fun getListSymbolForWashing(): List<SymbolGuide.SymbolForWashing> {
        val list = _listOfSymbols.value.orEmpty().toMutableList()
        return list.filterIsInstance<SymbolGuide.SymbolForWashing>()
    }

    private fun addCardToViewModel(context: Context) {
        val id = _cardId.value ?: return
        viewModelScope.launch {
            _card.value = updateCardUC.getCard(id)
                .first()
            addCardInfoToViewModel(context)
        }
    }

    fun setName(name: String) {
        if (name == _nameOfCloth.value) return
        else _nameOfCloth.value = name
    }

    fun setCategory(category: Category) {
        _category.value = category
    }

    private fun addCardInfoToViewModel(context: Context) {
        val card = _card.value ?: return
        _nameOfCloth.value = card.name
        _uri.value = card.picture?.toUri()
        viewModelScope.launch {
            _listOfSymbols.value = updateCardUC.getSymbolsByCardId(card.cardId).first()
                .map { symbol -> symbol.toSymbolForWashing(context) }
        }
    }

    fun showDialog(symbol: SymbolGuide.SymbolForWashing, context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val dialog: AlertDialog = builder.setTitle(context.getString(R.string.delete_symbol))
            .setPositiveButton(context.getString(R.string.ok_button)) { _, _ -> deleteSymbol(symbol) }
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
        _listOfSymbols.value = list

    }

    fun saveCardChanges(
        name: String,
        picture: String?,
        symbols: List<SymbolForWashingDBO>,
        doOnComplete: () -> Unit
    ) {
        val oldCard = _card.value ?: return
        val cardId = oldCard.cardId
        val card = Card(
            cardId = oldCard.cardId,
            name = name,
            picture = picture,
            category = oldCard.category
        )
        viewModelScope.launch {
            updateCardUC.updateCard(card)
            updateCardUC.deleteOldSymbols(cardId)
            symbols.forEach { symbol ->
                Log.d("SIMBOLI", "symbol for updating $symbol")
                addNewCardUC.addPairCardAndSymbol(
                    CardsAndSymbols(
                        0,
                        cardId,
                        symbol
                    )
                )
            }
            doOnComplete.invoke()
        }
    }

    fun addInfoOfNewCardToDataBase(
        nameOfCloth: String,
        imageUri: String?,
        listSymbols: List<SymbolForWashingDBO>,
        doOnComplete: () -> Unit
    ) {
        createNewCard(nameOfCloth, imageUri)
        viewModelScope.launch {
            addNewCardUC.addNewCard(_card.value)
            val cardId = addNewCardUC.getLastCardId()
            listSymbols.forEach { symbol ->
                addNewCardUC.addPairCardAndSymbol(
                    CardsAndSymbols(
                        pairId = 0,
                        cardAndSymbolId = cardId,
                        symbol = symbol
                    )
                )
            }
            doOnComplete.invoke()
        }
    }

    private fun createNewCard(nameOfCloth: String, imageUri: String?) {
        val category = _category.value?.toCategoryDBO(context)
        if (category != null) {
            _card.value = Card(0, nameOfCloth, imageUri, category)
        }
    }


}



