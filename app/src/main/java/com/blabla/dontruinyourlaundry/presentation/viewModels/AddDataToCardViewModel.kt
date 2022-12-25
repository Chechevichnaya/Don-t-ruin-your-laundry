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

    private val _lastCardId = MutableLiveData<Long>()

    private var listOfSymbolsDBO = listOf<SymbolForWashingDBO>()


    private val _listOfSymbols = MutableLiveData<List<SymbolGuide>>()
    val listOfSymbols: LiveData<List<SymbolGuide>> = _listOfSymbols

    private val _listOfSymbolsForFragmentChooseSymbols =
        MutableLiveData<List<SymbolGuide.SymbolForWashing>>()
    val listOfSymbolsForFragmentChooseSymbols: LiveData<List<SymbolGuide.SymbolForWashing>> =
        _listOfSymbolsForFragmentChooseSymbols

    private val _nameOfCloth = MutableLiveData<String>()
    val nameOfCloth: LiveData<String> = _nameOfCloth

    private val _card = MutableLiveData<Card?>()
    val card: LiveData<Card?> = _card

    private val _cardId = MutableLiveData<Long>()

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    private val _uri = MutableLiveData<Uri>()
    val uri: LiveData<Uri> = _uri

//    fun addSymbolsDBO(symbols: List<SymbolForWashingDBO>) {
//        _ListOfSymbolsDBO.value = symbols
//    }

    fun addCardForEditing(cardId: Long, context: Context) {
        if (cardId > 0 && _cardId.value == null) {
            _cardId.value = cardId
            addCardToViewModel(context)
        }
    }

    fun checkIfThereIsItemAddNewSymbol(items: List<SymbolGuide>): List<SymbolGuide> {
         return if ((items.last() is SymbolGuide.ButtonAddNewSymbol).not()) {
            val itemsList = items.toMutableList()
                itemsList.add(
                SymbolGuide.ButtonAddNewSymbol(
                    R.drawable.ic_add,
                    "Добавить новые символы"
                )
            )
             itemsList
        }
        else items
    }

    fun addItemAddNewSymbol() {
        val listOfSymbols = _listOfSymbols.value.orEmpty().toMutableList()
        listOfSymbols.add(
            SymbolGuide.ButtonAddNewSymbol(
                R.drawable.ic_add,
                "Добавить новые символы"
            )
        )
        _listOfSymbols.value = listOfSymbols

    }

    fun getListSymbolForWashing(): List<SymbolGuide.SymbolForWashing> {
        val list = _listOfSymbols.value.orEmpty().toMutableList()
        return list.filterIsInstance<SymbolGuide.SymbolForWashing>()
    }


    fun addInfoToDataBase(
        nameOfCloth: String,
        imageUri: String?,
        listSymbols: List<SymbolForWashingDBO>,
        doOnComplete: () -> Unit
    ) {
        createCard(nameOfCloth, imageUri)
        viewModelScope.launch {
            addNewCardUC.addNewCard(_card.value)
            Log.d("CHECK", "I am inside addNewCard ($card)")
            val cardId = addNewCardUC.getLastCardId().first()
            listSymbols.forEach { symbol ->
                addNewCardUC.addPairCardAndSymbol(
                    CardsAndSymbols(
                        pairId = 0,
                        cardId = cardId,
                        symbol = symbol
                    )
                )
            }
            doOnComplete.invoke()
            Log.d("CHECK", "I am VSEEEE")

        }
        //viewModel.addCardToDataBase(getNameOfCloth(), imageUri)
        //        viewModel.addPairCardAndSymbolToDB(getListOfSymbolForDB()!! as List<SymbolForWashingDBO>)

    }

    fun addPairCardAndSymbolToDB(listSymbols: List<SymbolForWashingDBO>) {
        Log.d("CHECK", "I am inside addPair")
        viewModelScope.launch {
            val cardId = addNewCardUC.getLastCardId().first()
            listSymbols.forEach { symbol ->
                addNewCardUC.addPairCardAndSymbol(
                    CardsAndSymbols(
                        pairId = 0,
                        cardId = cardId,
                        symbol = symbol
                    )
                )
            }
            Log.d("CHECK", "I am VSEEEE")

        }


    }

    private fun addCardToViewModel(context: Context) {
        val id = _cardId.value ?: return
        viewModelScope.launch {
            updateCardUC.getCard(id)
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

//    getListOfSymbolFroDB()?.listOfSymbols.isNullOrEmpty())
//     private fun getListOfSymbolFroDB(): ListOfSymbolsForDataBase? {
//        val listOfSymbols = viewModel.listOfSymbols.value?.let { list ->
//            val listDBO = list.map { item -> item.toSymbolForWashingDBO(context) }
//            ListOfSymbolsForDataBase(listDBO as List<SymbolForWashingDBO>)
//        }
//        if (listOfSymbols != null) {
//            viewModel.addSymbolsDBO(listOfSymbols.listOfSymbols)
//        }
//        return listOfSymbols
//    }


    private fun addCardInfoToViewModel(card: Card, context: Context) {
        _nameOfCloth.value = card.name
        _uri.value = card.picture?.toUri()
        var listSymbols = _listOfSymbols.value.orEmpty()
        viewModelScope.launch {
            updateCardUC.getSymbolsByCardId(card.cardId).collect {
                listSymbols = it.map { symbol -> symbol.toSymbolForWashing(context) }
            }
        }
        _listOfSymbols.value = listSymbols
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
        _listOfSymbols.value = list

    }

    fun createCard(name: String, uri: String?) {
        createNewCard(name, uri)
        val card = _card.value

        //addNewCard(card)
    }

    private fun addNewCard(card: Card?) {
        viewModelScope.launch {
            addNewCardUC.addNewCard(card)
            Log.d("CHECK", "I am inside addNewCard ($card)")
        }
    }

    fun saveCardChanges(
        name: String,
        picture: String?,
        symbols: List<SymbolForWashingDBO>
    ) {
        val oldCard = _card.value ?: return
        val card = Card(
            cardId = oldCard.cardId,
            name = name,
            picture = picture,
            category = oldCard.category
        )
        updateCard(card)
        updateCardAndSymbol(symbols)
    }

    private fun updateCardAndSymbol(symbols: List<SymbolForWashingDBO>) {
        val cardId = addNewCardUC.getLastCardId().asLiveData().value
        if (cardId != null) {
            symbols.forEach { symbol ->
                useCoorutineToupdateCardAndSymbol(
                    CardsAndSymbols(
                        0,
                        cardId,
                        symbol
                    )
                )
            }
        }


    }

    private fun useCoorutineToupdateCardAndSymbol(pair: CardsAndSymbols) {
        viewModelScope.launch {
            updateCardUC.updateCardAndSymbol(pair)
        }
    }

    private fun updateCard(card: Card) {
        viewModelScope.launch {
            updateCardUC.updateCard(card)
        }
    }

    fun createNewCard(nameOfCloth: String, imageUri: String?) {
        val category = _category.value?.toCategoryDBO(context)
        Log.d("CHECK", "card in createNewCard $category")
        if (category != null) {
            _card.value = Card(0, nameOfCloth, imageUri, category)

        }
    }


}



