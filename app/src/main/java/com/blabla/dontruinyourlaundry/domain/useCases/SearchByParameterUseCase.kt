package com.blabla.dontruinyourlaundry.domain.useCases

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.domain.entity.*

class SearchByParameterUseCase(private val repo: Repository) {

    fun getSelectedItems(itemsSearchByPameter: List<SearchScreenItem>):
            List<SearchScreenItem> {
        return itemsSearchByPameter.filter { it is SearchScreenItem.SearchParameter && it.selected }
    }

    private fun getTitle(item: SearchScreenItem.SearchParameter): TitleSearchByParameterEnum {
        return TitleSearchByParameterEnum.values()
            .first { titleEnum -> titleEnum.getParameters().contains(item.name) }
    }

    fun getSearchItems(): List<SearchScreenItem> {
        val listSearchItems = mutableListOf<SearchScreenItem>()
        TitleSearchByParameterEnum.values().forEach { titleEnum ->
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

    fun onItemClicked(clickedItem: SearchScreenItem.SearchParameter, listOfSearchItems: List<SearchScreenItem>): List<SearchScreenItem> {
        val titleOfClickedItem = getTitle(clickedItem)
        return when (titleOfClickedItem.getSelectionType()) {
            SelectionType.SINGLE -> {
                singleType(listOfSearchItems, clickedItem)
            }
            SelectionType.MULTI -> {
                multiType(listOfSearchItems, clickedItem)
            }
        }
    }

    private fun singleType(
        listAllItems: List<SearchScreenItem>,
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
        listAllItems: List<SearchScreenItem>,
        clickedItem: SearchScreenItem.SearchParameter
    ): List<SearchScreenItem> {
        return listAllItems.map { itemInList ->
            if (itemInList is SearchScreenItem.SearchParameter && itemInList == clickedItem) {
                itemInList.copy(selected = !itemInList.selected)
            } else itemInList
        }
    }

    fun getSelectedItemsNames(selectedItems: List<SearchScreenItem>): List<SearchParameterEnum>{
        val listSelectedItemsNames = mutableListOf<SearchParameterEnum>()
        selectedItems.forEach { selectedItem ->
            if (selectedItem is SearchScreenItem.SearchParameter) {
                listSelectedItemsNames.add(selectedItem.name)
            }
        }
        return listSelectedItemsNames
    }

    suspend fun getCardsSearchByParameter(listOfSelectedParameters: List<SearchParameterEnum>): List<Card> {
        val listOfCategory = mutableListOf<CategoryEnum>()
        val listOfAttachedSymbols = mutableListOf<SymbolForWashingDBO>()
        listOfSelectedParameters.forEach { item ->
            if (item.getCategory() != null) {
                listOfCategory.add(item.getCategory()!!)
            } else {
                listOfAttachedSymbols.addAll(item.getAttachedSymbols())
            }
        }
        val cards = getCardsSearchByCategory(listOfCategory) + getCardsSearchBySymbols(
            listOfAttachedSymbols
        )
        return cards.toSet().toList()
    }

    private suspend fun getCardsSearchByCategory(listOfCategory: MutableList<CategoryEnum>): List<Card> {
        return repo.getCardsSearchByPaCategory(listOfCategory)
    }

    private suspend  fun getCardsSearchBySymbols(listOfAttachedSymbols: MutableList<SymbolForWashingDBO>): List<Card> {
        return repo.getCardsSearchBySymbols(listOfAttachedSymbols)
    }

}