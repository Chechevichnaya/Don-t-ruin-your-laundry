package com.blabla.dontruinyourlaundry.data.dataBase

import androidx.room.*
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {
    @Insert
    suspend fun insertInDataBase(card: Card)

    @Update
    fun updateCardInDataBase(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)

    @Query("SELECT * FROM card WHERE category = :category ORDER BY name ASC")
    fun getCardsByCategory(category: CategoryEnum): Flow<List<Card>>

    @Query("SELECT cardId FROM card WHERE cardId = (SELECT MAX(cardId) FROM card)")
    fun getIdOfLastAddedCard():Flow<Long>

    @Query("SELECT * FROM card WHERE name = :name ORDER BY name ASC")
    fun getCardsByName(name: String): Flow<List<Card>>

    @Query("SELECT name FROM card")
    fun getAllNames(): Flow<List<String>>

    @Query("SELECT * FROM card WHERE cardId = :id")
    fun getOneCard(id: Long): Flow<Card>

    @Query("SELECT * FROM card WHERE category IN (:categories) ORDER BY name ASC")
    fun searchByParameterCategory(
        categories: List<CategoryEnum>,
    ): Flow<List<Card>>

//    @Query("SELECT * FROM card WHERE list_of_symbols IN (:symbols) ORDER BY name ASC")
//    fun searchByParameterSymbols(symbols: List<SymbolForWashingDBO>): Flow<List<Card>>

    @Update
    suspend fun update(card: Card)
}