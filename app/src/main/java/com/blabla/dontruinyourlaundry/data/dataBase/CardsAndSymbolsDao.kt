package com.blabla.dontruinyourlaundry.data.dataBase

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CardsAndSymbolsDao {

    @Insert
    suspend fun insertInDataBase(row: SymbolsAndCards)
}