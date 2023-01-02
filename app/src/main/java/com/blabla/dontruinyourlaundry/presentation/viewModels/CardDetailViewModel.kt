package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.content.Context
import android.util.Log
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
    val picture: LiveData<String?> = _picture

    private val _category = MutableLiveData<CategoryEnum>()
    val category: LiveData<CategoryEnum> = _category

    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

//     fun deleteCard(card: Card) {
//        viewModelScope.launch {
//            cardDetailUC.deleteCard(card)
//        }
//    }

    fun addListOfSymbolsToViewModel(card: Card) {
        viewModelScope.launch {
            val list = cardDetailUC.getSymbolsByCardId(card.cardId).first()
            Log.d("SYMBOLS", "symbols in fun addListOfSymbolsToViewModel $list")
            _listOfSymbols.value = list.map { symbol -> symbol.toSymbolForWashing(context) }
            Log.d("SYMBOLS", "_listOfSymbols.value ${_listOfSymbols.value}")

        }

    }

     fun getCardById(id: Long): LiveData<Card> {
        return cardDetailUC.getCardByIdFlow(id).asLiveData()
    }

    fun deleteCardAndSymbol(cardId: Long) {
        viewModelScope.launch {
            cardDetailUC.getPairByCardId(cardId)
            cardDetailUC.deleteCardAndSymbol(cardId)
        }
    }

    fun deleteInfo(cardId: Long, doOnComplete: () -> Unit) {
        viewModelScope.launch {
            cardDetailUC.deleteAllInfo(cardId)
//            cardDetailUC.deleteCardAndSymbol(cardId)
            doOnComplete.invoke()
        }


    }
}

