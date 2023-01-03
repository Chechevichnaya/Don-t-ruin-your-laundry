package com.blabla.dontruinyourlaundry.domain.useCases

import android.util.Log
import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchParameterEnum
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO

class SearchByParameterGetResultUseCase(private val repo: Repository) {

    private suspend fun getCardsSearchByCategory(listOfCategory: MutableList<CategoryEnum>): List<Card> {
        return repo.getCardsSearchByPaCategory(listOfCategory)
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

    private suspend fun getCardsSearchBySymbols(listOfAttachedSymbols: MutableList<SymbolForWashingDBO>): List<Card> {
        return repo.getCardsSearchBySymbols(listOfAttachedSymbols)
    }
}