package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.domain.useCases.ShowCardDetailUseCase
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
    val picture: LiveData<String?> = _picture

    private val _category = MutableLiveData<CategoryEnum>()
    val category: LiveData<CategoryEnum> = _category

    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    fun deleteCard(card: Card) {
        viewModelScope.launch {
            cardDetailUC.deleteCard(card)
        }
    }

    fun addListOfSymbolsToViewModel(card: Card) {
//        val list = card.listOfSymbols.listOfSymbols
//        _listOfSymbols.value =
//            list.map { item ->
//                item.toSymbolForWashing(context)
//            }
        var listSymbols = _listOfSymbols.value.orEmpty()
        viewModelScope.launch {
            cardDetailUC.getSymbolsByCardId(card.cardId).collect {
                listSymbols = it.map { symbol -> symbol.toSymbolForWashing(context) }
            }
        }
        _listOfSymbols.value = listSymbols
    }

    fun getCardById(id: Long): LiveData<Card> {
        return cardDetailUC.getCardById(id).asLiveData()
    }
}

