package com.blabla.dontruinyourlaundry.viewModels

import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.roomStuff.Card
import com.blabla.dontruinyourlaundry.roomStuff.CardsDao

class SearchByParametersResultViewModel(private val cardsDao: CardsDao) : ViewModel() {

    private val _listOfCategories = MutableLiveData<List<CategoryEnum>>()

    fun setListOfCategories(list: List<CategoryEnum>){
        _listOfCategories.value = list
    }

    fun getListOfCards(): LiveData<List<Card>> {
        val listOfCategories = _listOfCategories.value.orEmpty()
        return cardsDao.searchByParameterCategory(listOfCategories).asLiveData()
    }

    class SearchByParametersResultFactory(private val cardsDao: CardsDao) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchByParametersResultViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchByParametersResultViewModel(cardsDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}