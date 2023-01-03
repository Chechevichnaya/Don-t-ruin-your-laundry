package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchParameterEnum
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.domain.useCases.SearchByParameterGetResultUseCase
import kotlinx.coroutines.launch

class SearchByParametersResultViewModel(private val useCase: SearchByParameterGetResultUseCase) :
    ViewModel() {

    private val _listOfCategories = MutableLiveData<List<CategoryEnum>>()
    private val _listOfSelectedParameters = MutableLiveData<List<SearchParameterEnum>>()
    private val _listOfAttachedSymbols = MutableLiveData<List<SymbolForWashingDBO>>()
    private val _listOfCardsResult = MutableLiveData<List<Card>>()
    val listOfCardsResult: LiveData<List<Card>> = _listOfCardsResult


    fun setListOfSelectedParameters(list: List<SearchParameterEnum>) {
        _listOfSelectedParameters.value = list
        Log.d("RESULTING", "_listOfParameters.value ${_listOfSelectedParameters.value}")
    }


//    fun getListOfCards(): LiveData<List<Card>> {
//        val listOfCategories = _listOfCategories.value.orEmpty()
//        return cardsDao.searchByParameterCategory(listOfCategories).asLiveData()
//    }

    fun getListOfCards(): LiveData<List<Card>> {
        val listOfSelectedParameters = _listOfSelectedParameters.value.orEmpty()
        Log.d("CARDCARD", "listOfSelectedParameters ${listOfSelectedParameters}")
        viewModelScope.launch {
            _listOfCardsResult.value = useCase.getCardsSearchByParameter(listOfSelectedParameters)
        }
        return _listOfCardsResult
    }

    fun setListCardResult(list: List<Card>) {
        _listOfCardsResult.value = list
    }


}