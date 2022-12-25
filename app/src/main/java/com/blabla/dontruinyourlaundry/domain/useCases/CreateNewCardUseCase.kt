package com.blabla.dontruinyourlaundry.domain.useCases

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsAndSymbols
import kotlinx.coroutines.flow.Flow

class CreateNewCardUseCase(private val repo: Repository) {

    suspend fun addNewCard(card: Card?) {
        if (card != null) {
            repo.addNewCard(card)
        }
    }

    fun getLastCardId(): Flow<Long> {
         return repo.getIdOfLastAddedCard()
    }

    suspend fun addPairCardAndSymbol(pair: CardsAndSymbols){
        repo.addPairCardSymbols(pair)
    }


}