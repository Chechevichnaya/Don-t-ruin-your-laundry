package com.blabla.dontruinyourlaundry.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blabla.dontruinyourlaundry.data.SelectionType
import com.blabla.dontruinyourlaundry.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.entity.SearchScreenItem
import com.blabla.dontruinyourlaundry.entity.TitleEnum
import com.blabla.dontruinyourlaundry.roomStuff.CardsDao

class SearchByParametersViewModel(private val cardsDao: CardsDao) : ViewModel() {

    //    private val _searchItems = MutableLiveData(
//        getFullListOfSearchItems(DataForSearchByParameters.getData())
//    )
//    private val _searchItems = MutableLiveData(
//        SearchScreenItem.SearchParameter.
//    )
    private val _searchItems = MutableLiveData(getSearchItems())
    val searchItems: LiveData<List<SearchScreenItem>> = _searchItems

    private fun getSearchItems(): List<SearchScreenItem> {
        val listSearchItems = mutableListOf<SearchScreenItem>()
        TitleEnum.values().forEach { titleEnum ->
            listSearchItems.add(SearchScreenItem.Title(titleEnum))
            titleEnum.getParameters()
                .forEach { parameterEnum ->
                    listSearchItems.add(
                        SearchScreenItem.SearchParameter(
                            parameterEnum
                        )
                    )
                }
        }
        return listSearchItems
    }

    private val _selectedItems = MutableLiveData<List<SearchScreenItem>>()

    private val _listOfCategories = MutableLiveData<List<CategoryEnum>>()
    val listOfCategories: LiveData<List<CategoryEnum>> = _listOfCategories


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
//        val parametersCard = DataForSearchByParameters.getData().find { card ->
//            card.title == clickedItem.titleName
//        } ?: return

        val titleOfClickedItem = getTitle(clickedItem)
        val listOfSearchItems = _searchItems.value.orEmpty().toMutableList()
        _searchItems.value = when (titleOfClickedItem.getSelectionType()) {
            SelectionType.SINGLE -> {
                singleType(listOfSearchItems, clickedItem)
            }
            SelectionType.MULTI -> {
                multiType(listOfSearchItems, clickedItem)
            }
        }

//        val flatList = _searchItems.value ?: return
//        val flatListMutable = flatList.toMutableList()

//        _searchItems.value = when (parametersCard.selectionType) {
//            SelectionType.SINGLE -> {
//                singleType(flatListMutable, clickedItem)
//            }
//            SelectionType.MULTI -> {
//                multiType(flatListMutable, clickedItem)
//            }
//        }
    }

    private fun getTitle(item: SearchScreenItem.SearchParameter): TitleEnum {
        return TitleEnum.values()
            .first { titleEnum -> titleEnum.getParameters().contains(item.name) }
    }


    private fun singleType(
        listAllItems: MutableList<SearchScreenItem>,
        clickedItem: SearchScreenItem.SearchParameter
    ): List<SearchScreenItem> {
        return listAllItems.map { itemInList ->
            if (itemInList is SearchScreenItem.SearchParameter &&
                getTitle(itemInList) == getTitle(clickedItem)
            ) {
                if (clickedItem.selected) {
                    itemInList.copy(selected = false)
                } else {
                    itemInList.copy(selected = itemInList == clickedItem)
                }
            } else itemInList
        }
    }

    private fun multiType(
        listAllItems: MutableList<SearchScreenItem>,
        clickedItem: SearchScreenItem.SearchParameter
    ): List<SearchScreenItem> {
        return listAllItems.map { itemInList ->
            if (itemInList is SearchScreenItem.SearchParameter && itemInList == clickedItem) {
                itemInList.copy(selected = !itemInList.selected)
            } else itemInList
        }


    }

    fun processSelectedItems() {
        getSelectedItems()
        getListOfCategories()
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

    fun itemsSelected(): Boolean {
        val selectedItems = _searchItems.value.orEmpty().toMutableList()
            .filter { it is SearchScreenItem.SearchParameter && it.selected }
        return selectedItems.isNotEmpty()
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