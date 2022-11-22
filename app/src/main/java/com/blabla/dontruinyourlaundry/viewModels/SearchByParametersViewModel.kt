package com.blabla.dontruinyourlaundry.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.roomStuff.CardsDao
import com.blabla.dontruinyourlaundry.data.DataForSearchByParameters
import com.blabla.dontruinyourlaundry.data.SearchByParametersCard
import com.blabla.dontruinyourlaundry.data.SelectionType
import com.blabla.dontruinyourlaundry.entity.SearchScreenItem

class SearchByParametersViewModel(private val cardsDao: CardsDao) : ViewModel() {

//    fun getCardsByCategory(categories: List<Category>): LiveData<List<Card>> {
//        return cardsDao.searchByParameterCategory(categories).asLiveData()
//    }

//    fun getCardsBySymbols(symbols: List<SymbolForWashing>):LiveData<List<Card>>{
//        return cardsDao.searchByParameterSymbols(symbols).asLiveData()
//    }

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
                Log.d("clicked", "type single")
                singleType(flatListMutable, clickedItem)
            }
            SelectionType.MULTI -> {
                Log.d("clicked", "type multi")
                multiType(flatListMutable, clickedItem)
            }
        }
        Log.d("clicked", "new - ${_searchItems.value}")

//        when (parametersCard.selectionType) {
//            SelectionType.SINGLE -> {
//                flatListMutable.forEach { itemInList ->
//                    if (itemInList is SearchScreenItem.SearchParameter && itemInList.titleName == clickedItem.titleName) {
//                        if (clickedItem.selected) itemInList.selected = false
//                        else itemInList.selected = itemInList == clickedItem
//                    }
//                }
//            }
//            SelectionType.MULTI -> {
//                flatListMutable.forEach { itemInList ->
//                    if (itemInList is SearchScreenItem.SearchParameter && itemInList.titleName == clickedItem.titleName) {
//                        itemInList.selected = !itemInList.selected
//                    }
//                }
//            }
//        }
//        _searchItems.value = flatListMutable
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