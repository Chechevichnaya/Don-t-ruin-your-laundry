package com.blabla.dontruinyourlaundry.RoomStuff

import androidx.room.*
import com.blabla.dontruinyourlaundry.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {
    @Insert
    suspend fun insertInDataBase(card: Card)

    @Update
    fun updateCardInDataBase(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)

//    @Query("SELECT(SELECT COUNT(*) FROM card WHERE category = :category ) == 0")
//    suspend fun isEmpty(category: Category): Boolean

//    @Query("SELECT * FROM card")
//    fun getAllCards(): LiveData<List<Card>>

    @Query("SELECT * FROM card WHERE category = :category ORDER BY name ASC")
    fun getAllClothCardsByCategory(category: Category): Flow<List<Card>>

    @Query("SELECT * FROM card WHERE id = :id")
    fun getOneCard(id: Long): Flow<Card>

//    @Query("SELECT * FROM card LIMIT 1")
//    fun getAnyRowInTable(): Card?


}