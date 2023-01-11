package com.blabla.dontruinyourlaundry.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchParameterEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchScreenItem
import com.blabla.dontruinyourlaundry.domain.useCases.SearchByParameterUseCase
import kotlinx.coroutines.launch

class SearchByParametersViewModel(private val useCase: SearchByParameterUseCase) :
    ViewModel() {

    private val _searchItems = MutableLiveData(useCase.getSearchItems())
    val searchItems: LiveData<List<SearchScreenItem>> = _searchItems

    private val _selectedItems = MutableLiveData<List<SearchScreenItem>>()

    private val _listOfSearchParametersEnum = MutableLiveData<List<SearchParameterEnum>>()
    val listOfSearchParametersEnum: LiveData<List<SearchParameterEnum>> =
        _listOfSearchParametersEnum

    private val _listOfCardsResult = MutableLiveData<List<Card>>()

    private fun getSelectedItemsNames() {
        val selectedItems = _selectedItems.value.orEmpty()
        _listOfSearchParametersEnum.value = useCase.getSelectedItemsNames(selectedItems)
    }

    fun onItemClicked(clickedItem: SearchScreenItem.SearchParameter) {
        _searchItems.value = useCase.onItemClicked(clickedItem, _searchItems.value.orEmpty())
    }

    fun processSelectedItems() {
        getSelectedItemsNames()
    }



    private fun getSelectedItems() {
        _selectedItems.value =
            useCase.getSelectedItems(_searchItems.value.orEmpty().toMutableList())
    }

    fun checkIfItemsSelected(): Boolean {
        getSelectedItems()
        return _selectedItems.value?.isNotEmpty() ?: return false
    }

    fun getListOfCards(): LiveData<List<Card>> {
        val listOfSelectedParameters = _listOfSearchParametersEnum.value.orEmpty()
        viewModelScope.launch {
            _listOfCardsResult.value = useCase.getCardsSearchByParameter(listOfSelectedParameters)
        }
        return _listOfCardsResult
    }


}
