package com.blabla.dontruinyourlaundry.RoomStuff

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(value = [Converters::class])
@Database(entities = [Card::class], version = 1)
abstract class CardsDataBase : RoomDatabase() {
    abstract val cardsDao: CardsDao
    //val db = Room.databaseBuilder(
    //            applicationContext,
    //            AppDatabase::class.java, "database-name"
    //        ).build()

    companion object{
        @Volatile
        private var INSTANCE:CardsDataBase? = null

        fun getDatabase(context: Context): CardsDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CardsDataBase::class.java,
                    "card_database")
//                    .createFromAsset("database/card_cloth_table.db")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}