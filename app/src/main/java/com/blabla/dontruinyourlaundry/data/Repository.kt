package com.blabla.dontruinyourlaundry.data

import android.content.Context
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsAndSymbolsDao
import com.blabla.dontruinyourlaundry.data.dataBase.CardsDao
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.ListOfCards
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide
import kotlinx.coroutines.flow.Flow

class Repository(
    private val cardsDao: CardsDao,
    private val cardsAndSymbols: CardsAndSymbolsDao,
    private val context: Context
) {

    fun getSymbolGuideList(): List<SymbolGuide> = ListOfCards.loadListOfSymbolGuide(context)


    fun allCardsByCategory(category: CategoryEnum): Flow<List<Card>> =
        cardsDao.getCardsByCategory(category)


    fun getCardById(id: Long): Flow<Card> = cardsDao.getOneCard(id)


    fun getCardsByName(name: String): Flow<List<Card>> =
        cardsDao.getCardsByName(name)


    fun getAllNames(): Flow<List<String>> = cardsDao.getAllNames()


    fun getLastAddedCard(): Flow<Card> = cardsDao.getLastAddedCard()


    suspend fun addNewCard(card: Card?) {
        if (card != null) {
            cardsDao.insertInDataBase(card)
        }
    }

    suspend fun updateCard(card: Card) = cardsDao.update(card)


    suspend fun deleteCard(card: Card) = cardsDao.deleteCard(card)

}