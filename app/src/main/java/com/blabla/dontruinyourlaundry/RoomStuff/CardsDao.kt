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
    fun getCardsByCategory(category: Category): Flow<List<Card>>

    @Query("SELECT * FROM card WHERE name = :name ORDER BY name ASC")
    fun getCardsByName(name:String):Flow<List<Card>>

    @Query("SELECT name FROM card")
    fun getAllNames():Flow<List<String>>

    @Query("SELECT * FROM card WHERE id = :id")
    fun getOneCard(id: Long): Flow<Card>
}