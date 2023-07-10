package com.blabla.dontruinyourlaundry.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blabla.dontruinyourlaundry.data.database.Card
import com.blabla.dontruinyourlaundry.domain.entity.SearchParameterEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchScreenItem
import com.blabla.dontruinyourlaundry.domain.useCases.SearchByParameterResultUseCase
import kotlinx.coroutines.launch

class SearchByParametersResultViewModel(
    private val useCase: SearchByParameterResultUseCase
) : ViewModel() {

    private val _searchItems = MutableLiveData(useCase.getSearchItems())

    private val _listOfSelectedParameters = MutableLiveData<List<SearchParameterEnum>>()

    private val _listOfSearchItemsOnlyWithSelectedItems = MutableLiveData<List<SearchScreenItem>>()

    private val _listOfCardsResult = MutableLiveData<List<Card>>()

    fun setListOfSelectedParameters(list: List<SearchParameterEnum>) {
        _listOfSelectedParameters.value = list
    }


    fun getListOfCards(): LiveData<List<Card>> {
        val listOfSelectedParameters = _listOfSelectedParameters.value.orEmpty()
        viewModelScope.launch {
            _listOfCardsResult.value = useCase.getCardsSearchByParameter(listOfSelectedParameters)
        }
        return _listOfCardsResult
    }

    fun getListSearchItemsWithSelectedItems(): LiveData<List<SearchScreenItem>> {
        val searchItems = _searchItems.value.orEmpty().toMutableList()
        val selectedItems = _listOfSelectedParameters.value.orEmpty().toMutableList()
        val listOfTitles = selectedItems.map { it.getTitle() }

        val listForRecyclerView = searchItems.filter {
            (it is SearchScreenItem.SearchParameter && selectedItems.contains(
                it.name
            )) || (it is SearchScreenItem.Title && listOfTitles.contains(it))
        }
        _listOfSearchItemsOnlyWithSelectedItems.value = listForRecyclerView
        return _listOfSearchItemsOnlyWithSelectedItems
    }
}