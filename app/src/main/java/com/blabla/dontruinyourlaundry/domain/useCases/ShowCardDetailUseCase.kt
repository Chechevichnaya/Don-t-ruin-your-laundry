package com.blabla.dontruinyourlaundry.domain.useCases

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsAndSymbols
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import kotlinx.coroutines.flow.Flow

class ShowCardDetailUseCase(private val repo:Repository) {

    suspend fun deleteCard(card: Card) {
            repo.deleteCard(card)

    }

    fun getCardById(id: Long): Flow<Card> {
        return repo.getCardById(id)
    }

    fun getSymbolsByCardId(cardId: Long): Flow<List<SymbolForWashingDBO>> {
        return repo.getSymbolsByCardId(cardId)

    }

    suspend fun deleteCardAndSymbol(cardId: Long) {
        val listOfCardsAndSymbols = repo.getPairByCardId(cardId)
        repo.deleteCardAndSymbol(listOfCardsAndSymbols)
    }

    suspend fun getPairByCardId(cardId: Long) {
        repo.getPairByCardId(cardId)
    }


}