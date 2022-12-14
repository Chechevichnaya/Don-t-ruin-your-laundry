package com.blabla.dontruinyourlaundry.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.data.DataForSearchByParameters
import com.blabla.dontruinyourlaundry.data.SearchByParametersCard
import com.blabla.dontruinyourlaundry.data.SelectionType
import com.blabla.dontruinyourlaundry.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.entity.SearchScreenItem
import com.blabla.dontruinyourlaundry.entity.SymbolGuide
import com.blabla.dontruinyourlaundry.roomStuff.Card
import com.blabla.dontruinyourlaundry.roomStuff.CardsDao
import kotlinx.coroutines.launch

class SearchByParametersViewModel(private val cardsDao: CardsDao) : ViewModel() {

    private val _searchItems = MutableLiveData(
        getFullListOfSearchItems(DataForSearchByParameters.getData())
    )
    val searchItems: LiveData<List<SearchScreenItem>> = _searchItems

    private val _selectedItems = MutableLiveData<List<SearchScreenItem>>()
    val selectedItems: LiveData<List<SearchScreenItem>> = _selectedItems

    private val _listOfCategories = MutableLiveData<List<CategoryEnum>>()
    val listOfCategories: LiveData<List<CategoryEnum>> = _listOfCategories


    private fun getFullListOfSearchItems(input: List<SearchByParametersCard>): List<SearchScreenItem> {
        val result = mutableListOf<SearchScreenItem>()

        input.forEach { parametersCard ->
            result.add(
                SearchScreenItem.Title(parametersCard.title)
            )
            parametersCard.listOfButton.forEach { button ->
                result.add(
                    SearchScreenItem.SearchParameter(
                        name = button.name,
                        titleName = parametersCard.title
                    )
                )
            }
        }
        return result
    }

    fun onItemClicked(clickedItem: SearchScreenItem.SearchParameter) {
        val parametersCard = DataForSearchByParameters.getData().find { card ->
            card.title == clickedItem.titleName
        } ?: return


        val flatList = _searchItems.value ?: return
        val flatListMutable = flatList.toMutableList()

        _searchItems.value = when (parametersCard.selectionType) {
            SelectionType.SINGLE -> {
                singleType(flatListMutable, clickedItem)
            }
            SelectionType.MULTI -> {
                multiType(flatListMutable, clickedItem)
            }
        }
    }


    private fun singleType(
        flatList: MutableList<SearchScreenItem>,
        clickedItem: SearchScreenItem.SearchParameter
    ): List<SearchScreenItem> {
        return flatList.map { itemInList ->
            if (itemInList is SearchScreenItem.SearchParameter && itemInList.titleName == clickedItem.titleName) {
                if (clickedItem.selected) {
                    itemInList.copy(selected = false)
                } else {
                    itemInList.copy(selected = itemInList == clickedItem)
                }
            } else itemInList
        }
    }

    private fun multiType(
        flatList: MutableList<SearchScreenItem>,
        clickedItem: SearchScreenItem.SearchParameter
    ): List<SearchScreenItem> {
        return flatList.map { itemInList ->
            if (itemInList is SearchScreenItem.SearchParameter && itemInList == clickedItem) {
                itemInList.copy(selected = !itemInList.selected)
            } else itemInList
        }


    }

    private fun processSelectedItems() {
        getSelectedItems()
        getListOfCategories()
    }

    fun getListOfCards(): LiveData<List<Card>> {
        processSelectedItems()
        val listOfCategories = _listOfCategories.value.orEmpty()
        val flow = cardsDao.searchByParameterCategory(listOfCategories)
        return flow.asLiveData()
    }


    private fun getListOfCategories() {
        val selectedItems = _selectedItems.value.orEmpty()
        val listOfCategories = _listOfCategories.value.orEmpty().toMutableList()
//        val allItems = _searchItems.value.orEmpty()
        selectedItems.forEach { selectedItem ->
            if (selectedItem is SearchScreenItem.SearchParameter) {
                listOfCategories.add(selectedItem.getCategory())
            }
        }
        _listOfCategories.value = listOfCategories
    }

    private fun getSelectedItems() {
        val selectedItems = _searchItems.value.orEmpty().toMutableList()
            .filter { it is SearchScreenItem.SearchParameter && it.selected }
        _selectedItems.value = selectedItems
        Log.d("SEARCH", "_selectedItems.value = ${_selectedItems.value}")

    }


}

class SearchByParametersFactory(private val cardsDao: CardsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchByParametersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchByParametersViewModel(cardsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}