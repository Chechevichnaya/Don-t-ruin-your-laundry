package com.blabla.dontruinyourlaundry.data.dataBase

import androidx.room.*
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsAndSymbolsDao {

    @Insert
    suspend fun insertInDataBase(row: CardsAndSymbols)

    @Update
    suspend fun update(pair: CardsAndSymbols)

    @Query("SELECT symbol FROM cardsandsymbols WHERE cardAndSymbolId = :cardId")
    fun getListOfSymbolsByCardId(cardId: Long): Flow<List<SymbolForWashingDBO>>

    @Query("SELECT * FROM cardsandsymbols WHERE cardAndSymbolId = :cardId")
    suspend fun getPairByCardId(cardId: Long):List<CardsAndSymbols>

    @Delete
     suspend fun deleteCardAndSymbols(pair: List<CardsAndSymbols>)

     @Query("SELECT cardAndSymbolId FROM cardsandsymbols WHERE symbol IN (:listOfAttachedSymbols)")
     suspend fun getCardIdBySymbols(listOfAttachedSymbols: MutableList<SymbolForWashingDBO>): List<Long>


}