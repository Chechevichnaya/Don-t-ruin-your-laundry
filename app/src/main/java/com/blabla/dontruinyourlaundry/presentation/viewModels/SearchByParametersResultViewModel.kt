package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchParameterEnum
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsDao

class SearchByParametersResultViewModel(private val cardsDao: CardsDao) : ViewModel() {

    private val _listOfCategories = MutableLiveData<List<CategoryEnum>>()
    private val _listOfParameters = MutableLiveData<List<SearchParameterEnum>>()
    private val _listOfAttachedSymbols = MutableLiveData<List<SymbolForWashingDBO>>()
    private val _listOfCardsResult = MutableLiveData<List<Card>>()
    val listOfCardsResult:LiveData<List<Card>> = _listOfCardsResult


    fun setListOfCategories(list: List<CategoryEnum>) {
        _listOfCategories.value = list
    }

    fun setListOfParameters(list: List<SearchParameterEnum>) {
        _listOfParameters.value = list
        Log.d("RESULT", "_listOfParameters.value ${_listOfParameters.value}")
    }


//    fun getListOfCards(): LiveData<List<Card>> {
//        val listOfCategories = _listOfCategories.value.orEmpty()
//        return cardsDao.searchByParameterCategory(listOfCategories).asLiveData()
//    }

    fun getListOfCards(): LiveData<List<Card>> {
        val listOfAllParameters = _listOfParameters.value.orEmpty()
        val listOfCategory = _listOfCategories.value.orEmpty().toMutableList()
        val listOfAttachedSymbols = _listOfAttachedSymbols.value.orEmpty().toMutableList()
        listOfAllParameters.forEach { item ->
            Log.d("RESULT", "item in list ${item}")

            if (item.getCategory() != null) {
                listOfCategory.add(item.getCategory()!!)
                Log.d("RESULT", "listOfCategory ${listOfCategory}")
            } else {
                listOfAttachedSymbols.addAll(item.getAttachedSymbols())
            }
        }

        _listOfCardsResult.value = cardsDao.searchByParameterCategory(listOfCategory).asLiveData().value
        Log.d("RESULT", "list RESULT ${_listOfCardsResult.value}")
        return cardsDao.searchByParameterCategory(listOfCategory).asLiveData()
//        val cardsByCategory =
//            cardsDao.searchByParameterCategory(listOfCategory).asLiveData().value.orEmpty()
//        Log.d("RESULT", "cardsByCategory ${cardsByCategory}")
//        _listOfCardsResult.value = cardsByCategory
//        val listOfCards = _listOfCardsResult.value.orEmpty().toMutableList()
//        val cardsBySymbols =
//            cardsDao.searchByParameterSymbols(listOfAttachedSymbols).asLiveData().value.orEmpty()
//        listOfCards.addAll(cardsBySymbols)
//        _listOfCardsResult.value = listOfCards
//        return _listOfCardsResult
    }

    fun setListCardResult(list: List<Card>){
        _listOfCardsResult.value = list
    }

//    class SearchByParametersResultFactory(private val cardsDao: CardsDao) :
//        ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(SearchByParametersResultViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return SearchByParametersResultViewModel(cardsDao) as T
//            }
//            throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }
}