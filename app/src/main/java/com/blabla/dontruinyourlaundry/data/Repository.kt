package com.blabla.dontruinyourlaundry.data

import android.content.Context
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsAndSymbolsDao
import com.blabla.dontruinyourlaundry.data.dataBase.CardsDao
import com.blabla.dontruinyourlaundry.data.dataBase.CardsAndSymbols
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.ListOfCards
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide
import kotlinx.coroutines.flow.Flow

class Repository(
    private val cardsDao: CardsDao,
    private val cardsAndSymbolsDao: CardsAndSymbolsDao,
    private val context: Context
) {

    fun getSymbolGuideList(): List<SymbolGuide> = ListOfCards.loadListOfSymbolGuide(context)


    fun allCardsByCategory(category: CategoryEnum): Flow<List<Card>> =
        cardsDao.getCardsByCategory(category)


    fun getCardById(id: Long): Flow<Card> = cardsDao.getOneCard(id)


    fun getCardsByName(name: String): Flow<List<Card>> =
        cardsDao.getCardsByName(name)


    fun getAllNames(): Flow<List<String>> = cardsDao.getAllNames()

    suspend fun getIdOfLastAddedCard(): Long = cardsDao.getIdOfLastAddedCard()

    suspend fun addPairCardSymbols(pair: CardsAndSymbols) {
        cardsAndSymbolsDao.insertInDataBase(pair)
    }


    suspend fun addNewCard(card: Card?) {
        if (card != null) {
            cardsDao.insertInDataBase(card)
        }
    }

    suspend fun updateCard(card: Card) = cardsDao.update(card)


    suspend fun deleteCard(card: Card) = cardsDao.deleteCard(card)

    suspend fun updateCardAndSymbol(pair: CardsAndSymbols) = cardsAndSymbolsDao.update(pair)

    fun getSymbolsByCardId(cardId: Long): Flow<List<SymbolForWashingDBO>> {
        return cardsAndSymbolsDao.getListOfSymbolsByCardId(cardId)
    }

    suspend fun deleteCardAndSymbol(pairs: List<CardsAndSymbols>) =
        cardsAndSymbolsDao.deleteCardAndSymbols(pairs)

    suspend fun getPairByCardId(cardId: Long): List<CardsAndSymbols> {
        return cardsAndSymbolsDao.getPairByCardId(cardId)
    }

    suspend fun deleteListOfCardsAndSymbols(list: List<CardsAndSymbols>) =
        cardsAndSymbolsDao.deleteCardAndSymbols(list)

}