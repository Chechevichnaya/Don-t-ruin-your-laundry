package com.blabla.dontruinyourlaundry.domain.useCases

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.database.Card
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import kotlinx.coroutines.flow.Flow

class EditCardUseCase(private val repo: Repository) {
    suspend fun updateCard(card: Card) {
        repo.updateCard(card)
    }

    fun getCard(id: Long): Flow<Card> {
        return repo.getCardByIdFlow(id)
    }

    fun getSymbolsByCardId(cardId: Long): Flow<List<SymbolForWashingDBO>> {
        return repo.getSymbolsByCardId(cardId)
    }

    suspend fun deleteOldSymbols(cardId: Long) {
        val list = repo.getPairByCardId(cardId)
        repo.deleteListOfCardsAndSymbols(list)
    }
}