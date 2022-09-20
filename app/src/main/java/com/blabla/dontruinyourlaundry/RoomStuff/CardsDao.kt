package com.blabla.dontruinyourlaundry.RoomStuff

import androidx.lifecycle.LiveData
import androidx.room.*
import com.blabla.dontruinyourlaundry.entity.Category

@Dao
interface CardsDao {
    @Insert
      suspend fun insertInDataBase(card: Card)

    @Update
     fun updateCardInDataBase(card: Card)

    @Delete
     fun deleteCardFromDataBase(card: Card)

    @Query ("SELECT * FROM card")
    fun getAllCards(): LiveData<List<Card>>

    @Query ("SELECT * FROM card WHERE category = :category")
    fun getAllClothCardsByCategory(category: Category): LiveData<List<Card>>

    @Query("SELECT * FROM card WHERE name = :nameOfOneCard")
    fun getOneCard(nameOfOneCard: String): LiveData<Card>

    @Query("SELECT * FROM card LIMIT 1")
    fun getAnyRowInTable(): Card?


}