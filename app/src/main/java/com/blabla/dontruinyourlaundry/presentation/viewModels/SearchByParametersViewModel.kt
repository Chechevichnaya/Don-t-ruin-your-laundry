package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchParameterEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchScreenItem
import com.blabla.dontruinyourlaundry.domain.useCases.SearchByParameterUseCase

class SearchByParametersViewModel(private val searchByParameterUC: SearchByParameterUseCase) :
    ViewModel() {

    private val _searchItems = MutableLiveData(searchByParameterUC.getSearchItems())
    val searchItems: LiveData<List<SearchScreenItem>> = _searchItems

    private val _selectedItems = MutableLiveData<List<SearchScreenItem>>()

    private val _listOfCategories = MutableLiveData<List<CategoryEnum>>()
    val listOfCategories: LiveData<List<CategoryEnum>> = _listOfCategories

    private val _listOfSearchParametersEnum = MutableLiveData<List<SearchParameterEnum>>()
    val listOfSearchParametersEnum: LiveData<List<SearchParameterEnum>> =
        _listOfSearchParametersEnum

    private fun getSelectedItemsNames() {
        val selectedItems = _selectedItems.value.orEmpty()
        _listOfSearchParametersEnum.value = searchByParameterUC.getSelectedItemsNames(selectedItems)
    }
//    private fun getFullListOfSearchItems(input: List<SearchByParametersCard>): List<SearchScreenItem> {
//        val result = mutableListOf<SearchScreenItem>()
//
//        input.forEach { parametersCard ->
//            result.add(
//                SearchScreenItem.Title(parametersCard.title)
//            )
//            parametersCard.listOfButton.forEach { button ->
//                result.add(
//                    SearchScreenItem.SearchParameter(
//                        name = button.name,
//                        titleName = parametersCard.title
//                    )
//                )
//            }
//        }
//        return result
//    }

    fun onItemClicked(clickedItem: SearchScreenItem.SearchParameter) {
        _searchItems.value = searchByParameterUC.onItemClicked(clickedItem, _searchItems.value.orEmpty())
        Log.d("PARAM", "searchItems $_searchItems.value")
    }

    fun processSelectedItems() {
        getSelectedItemsNames()
//        getListOfCategories()
    }

//    private fun getListOfCategories() {
//        val selectedItems = _selectedItems.value.orEmpty()
//        val listOfCategories = _listOfCategories.value.orEmpty().toMutableList()
////        val allItems = _searchItems.value.orEmpty()
//        selectedItems.forEach { selectedItem ->
//            if (selectedItem is SearchScreenItem.SearchParameter) {
//                listOfCategories.add(selectedItem.getCategory())
//            }
//        }
//        _listOfCategories.value = listOfCategories
//    }

    private fun getSelectedItems() {
        _selectedItems.value =
            searchByParameterUC.getSelectedItems(_searchItems.value.orEmpty().toMutableList())
    }

    fun checkIfItemsSelected(): Boolean {
        getSelectedItems()
        return _selectedItems.value?.isNotEmpty() ?: return false

    }


}
