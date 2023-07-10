package com.blabla.dontruinyourlaundry.domain.useCases

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.database.Card
import com.blabla.dontruinyourlaundry.domain.entity.*

class SearchByParameterUseCase(private val repo: Repository) {

    fun getSelectedItems(itemsSearchByParameter: List<SearchScreenItem>):
            List<SearchScreenItem> {
        return itemsSearchByParameter.filter { it is SearchScreenItem.SearchParameter && it.selected }
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

    fun getSelectedSearchItems(
        clickedItem: SearchScreenItem.SearchParameter,
        listOfSearchItems: List<SearchScreenItem>
    ): List<SearchScreenItem> {
        val titleOfClickedItem = getTitle(clickedItem)
        return when (titleOfClickedItem.getSelectionType()) {
            SelectionType.SINGLE -> getSingleTypeItems(listOfSearchItems, clickedItem)
            SelectionType.MULTI -> getMultiTypeItems(listOfSearchItems, clickedItem)
        }
    }

    private fun getSingleTypeItems(
        items: List<SearchScreenItem>,
        clickedItem: SearchScreenItem.SearchParameter
    ): List<SearchScreenItem> {
        return items.map { item ->
            if (item is SearchScreenItem.SearchParameter &&
                getTitle(item) == getTitle(clickedItem)
            ) {
                if (clickedItem.selected) {
                    item.copy(selected = false)
                } else {
                    item.copy(selected = item == clickedItem)
                }
            } else item
        }
    }

    private fun getMultiTypeItems(
        items: List<SearchScreenItem>,
        clickedItem: SearchScreenItem.SearchParameter
    ): List<SearchScreenItem> {
        return items.map { item ->
            if (item is SearchScreenItem.SearchParameter && item == clickedItem) {
                item.copy(selected = !item.selected)
            } else item
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