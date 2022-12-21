package com.blabla.dontruinyourlaundry.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blabla.dontruinyourlaundry.domain.Converters

@TypeConverters(value = [Converters::class])
@Database(entities = [Card::class], version = 1)
abstract class CardsDataBase : RoomDatabase() {

    abstract val cardsDao: CardsDao

    companion object{
        @Volatile
        private var INSTANCE:CardsDataBase? = null

        fun getDatabase(context: Context): CardsDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CardsDataBase::class.java,
                    "card_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

