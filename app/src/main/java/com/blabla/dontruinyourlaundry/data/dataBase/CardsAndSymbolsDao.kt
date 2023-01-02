package com.blabla.dontruinyourlaundry.data.dataBase

import androidx.room.*
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsAndSymbolsDao {

    @Insert
    suspend fun insertInDataBase(row: CardsAndSymbols)

    @Update
    suspend fun update(pair: CardsAndSymbols)

    @Query("SELECT symbol FROM cardsandsymbols WHERE cardId = :cardId")
    fun getListOfSymbolsByCardId(cardId: Long): Flow<List<SymbolForWashingDBO>>

    @Query("SELECT * FROM cardsandsymbols WHERE cardId = :cardId")
    suspend fun getPairByCardId(cardId: Long):List<CardsAndSymbols>

    @Delete
     suspend fun deleteCardAndSymbols(pair: List<CardsAndSymbols>)




}