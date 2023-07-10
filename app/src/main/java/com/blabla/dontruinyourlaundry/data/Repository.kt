package com.blabla.dontruinyourlaundry.data

import android.content.Context
import com.blabla.dontruinyourlaundry.data.database.Card
import com.blabla.dontruinyourlaundry.data.database.CardsAndSymbolsDao
import com.blabla.dontruinyourlaundry.data.database.CardsDao
import com.blabla.dontruinyourlaundry.data.database.CardsAndSymbols
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuideItemProvider
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuideItem
import kotlinx.coroutines.flow.Flow

class Repository(
    private val cardsDao: CardsDao,
    private val cardsAndSymbolsDao: CardsAndSymbolsDao,
    private val context: Context
) {

    fun getSymbolGuideList(): List<SymbolGuideItem> = SymbolGuideItemProvider.getSymbolGuideItems(context)


    fun allCardsByCategory(category: CategoryEnum): Flow<List<Card>> =
        cardsDao.getCardsByCategory(category)


     fun getCardByIdFlow(id: Long): Flow<Card> = cardsDao.getOneCardFlow(id)


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

     suspend fun getCardById(id: Long): Card = cardsDao.getOneCard(id)


     suspend fun getCardsSearchByPaCategory(listOfCategory: MutableList<CategoryEnum>): List<Card> {
        return cardsDao.searchByParameterCategory(listOfCategory)
    }

    suspend fun getCardsSearchBySymbols(listOfAttachedSymbols: MutableList<SymbolForWashingDBO>): List<Card> {
        val cardId =  cardsAndSymbolsDao.getCardIdBySymbols(listOfAttachedSymbols)
        return cardsDao.getCardsByCardsId(cardId)
    }
}