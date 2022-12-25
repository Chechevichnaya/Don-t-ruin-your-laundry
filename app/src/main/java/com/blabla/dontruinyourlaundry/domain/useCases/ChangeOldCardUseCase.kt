package com.blabla.dontruinyourlaundry.domain.useCases

import androidx.lifecycle.viewModelScope
import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsAndSymbols
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChangeOldCardUseCase(private val repo: Repository) {

    suspend fun updateCard(card: Card) {
        repo.updateCard(card)
    }

    suspend fun getCard(id: Long): Flow<Card> {
        return repo.getCardById(id)
    }

    suspend fun updateCardAndSymbol(pair: CardsAndSymbols) {
        repo.updateCardAndSymbol(pair)

    }

    fun getSymbolsByCardId(cardId: Long): Flow<List<SymbolForWashingDBO>> {
        return repo.getSymbolsByCardId(cardId)

    }
}