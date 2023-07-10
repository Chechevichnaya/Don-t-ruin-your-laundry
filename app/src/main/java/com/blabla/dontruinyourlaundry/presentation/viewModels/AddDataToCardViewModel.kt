package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.database.Card
import com.blabla.dontruinyourlaundry.data.database.CardsAndSymbols
import com.blabla.dontruinyourlaundry.domain.entity.Category
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuideItem
import com.blabla.dontruinyourlaundry.domain.useCases.EditCardUseCase
import com.blabla.dontruinyourlaundry.domain.useCases.CreateNewCardUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddDataToCardViewModel(
    private val addNewCardUC: CreateNewCardUseCase,
    private val updateCardUC: EditCardUseCase,
    private val context: Context
) : ViewModel() {

    private val _listOfSymbols = MutableLiveData<List<SymbolGuideItem>>()
    val listOfSymbols: LiveData<List<SymbolGuideItem>> = _listOfSymbols

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

    fun getListSymbolForWashing(): List<SymbolGuideItem.SymbolForWashing> {
        val list = _listOfSymbols.value.orEmpty().toMutableList()
        return list.filterIsInstance<SymbolGuideItem.SymbolForWashing>()
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

    fun showDialog(symbol: SymbolGuideItem.SymbolForWashing, context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val drawable = ResourcesCompat.getDrawable(context.resources, symbol.pictureId, null)
        val wrappedDrawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(
            wrappedDrawable,
            ResourcesCompat.getColor(context.resources, R.color.icon_text, null)
        )
        val dialog: AlertDialog = builder.setTitle(context.getString(R.string.delete_symbol))
            .setPositiveButton(context.getString(R.string.ok_button)) { _, _ -> deleteSymbol(symbol) }
            .setIcon(symbol.pictureId)
            .create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.lilac_700,
                    null
                )
            )
    }


    private fun deleteSymbol(symbol: SymbolGuideItem.SymbolForWashing) {
        val list = _listOfSymbols.value.orEmpty().toMutableList()
        list.remove(symbol)
        _listOfSymbols.value = list
    }

    fun updateUri(newUri: Uri) {
        _uri.value = newUri
    }

    fun addSelectedSymbols(list: List<SymbolGuideItem.SymbolForWashing>) {
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



