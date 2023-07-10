package com.blabla.dontruinyourlaundry.domain.useCases

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.database.Card
import kotlinx.coroutines.flow.Flow

class SearchByNameUseCase(private val repo: Repository) {

    fun getCardsByName(name: String): Flow<List<Card>> {
        return repo.getCardsByName(name)
    }

    fun getAllNames():Flow<List<String>>{
        return repo.getAllNames()
    }
}