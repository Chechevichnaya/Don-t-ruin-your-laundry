package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.domain.useCases.ShowCardDetailUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CardDetailViewModel(
    private val cardDetailUC: ShowCardDetailUseCase,
    private val context: Context
) : ViewModel() {

    private val _listOfSymbols = MutableLiveData<List<SymbolGuide.SymbolForWashing>>()
    val listOfSymbols: LiveData<List<SymbolGuide.SymbolForWashing>> = _listOfSymbols

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _picture = MutableLiveData<String?>()

    private val _category = MutableLiveData<CategoryEnum>()
    val category: LiveData<CategoryEnum> = _category

    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    fun addListOfSymbolsToViewModel(card: Card) {
        viewModelScope.launch {
            val list = cardDetailUC.getSymbolsByCardId(card.cardId).first()
            _listOfSymbols.value = list.map { symbol -> symbol.toSymbolForWashing(context) }
        }

    }

    fun getCardById(id: Long): LiveData<Card> {
        return cardDetailUC.getCardByIdFlow(id).asLiveData()
    }

    fun deleteInfo(cardId: Long, doOnComplete: () -> Unit) {
        viewModelScope.launch {
            cardDetailUC.deleteAllInfo(cardId)
            doOnComplete.invoke()
        }
    }
}

