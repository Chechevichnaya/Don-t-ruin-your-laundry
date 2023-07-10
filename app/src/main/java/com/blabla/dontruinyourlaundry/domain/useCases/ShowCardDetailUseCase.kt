package com.blabla.dontruinyourlaundry.domain.useCases

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.database.Card
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import kotlinx.coroutines.flow.Flow

class ShowCardDetailUseCase(private val repo: Repository) {
    fun getCardByIdFlow(id: Long): Flow<Card> {
        return repo.getCardByIdFlow(id)
    }

    private suspend fun getCardById(id: Long): Card {
        return repo.getCardById(id)
    }

    fun getSymbolsByCardId(cardId: Long): Flow<List<SymbolForWashingDBO>> {
        return repo.getSymbolsByCardId(cardId)
    }

    private suspend fun deleteCardAndSymbol(cardId: Long) {
        val listOfCardsAndSymbols = repo.getPairByCardId(cardId)
        repo.deleteCardAndSymbol(listOfCardsAndSymbols)
    }

    suspend fun deleteAllInfo(cardId: Long) {
        deleteCardAndSymbol(cardId)
        val card = getCardById(cardId)
        repo.deleteCard(card)
    }
}