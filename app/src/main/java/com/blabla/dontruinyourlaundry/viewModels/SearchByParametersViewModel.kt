package com.blabla.dontruinyourlaundry.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blabla.dontruinyourlaundry.data.DataForSearchByParameters
import com.blabla.dontruinyourlaundry.data.SearchByParametersCard
import com.blabla.dontruinyourlaundry.data.SelectionType
import com.blabla.dontruinyourlaundry.entity.SearchScreenItem
import com.blabla.dontruinyourlaundry.roomStuff.CardsDao

class SearchByParametersViewModel(private val cardsDao: CardsDao) : ViewModel() {

    private val _searchItems = MutableLiveData(
        getFullListOfSearchItems(DataForSearchByParameters.getData())
    )
    val searchItems: LiveData<List<SearchScreenItem>> = _searchItems

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
            }
             else itemInList
        }


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