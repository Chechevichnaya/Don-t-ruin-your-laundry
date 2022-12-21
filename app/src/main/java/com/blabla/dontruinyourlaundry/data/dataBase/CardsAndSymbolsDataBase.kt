package com.blabla.dontruinyourlaundry.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SymbolsAndCards::class], version = 1)
abstract class CardsAndSymbolsDataBase : RoomDatabase() {

    abstract val cardsAndSymbolsDao: CardsAndSymbolsDao

    companion object {
        @Volatile
        private var INSTANCE: CardsAndSymbolsDataBase? = null

        fun getDataBase(context: Context): CardsAndSymbolsDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CardsAndSymbolsDataBase::class.java,
                    "cards_and_symbols_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

