package com.blabla.dontruinyourlaundry.data.dataBase

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import com.blabla.dontruinyourlaundry.domain.entity.ListOfSymbolsForDataBase
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Card(
    @PrimaryKey (autoGenerate = true) val cardId: Long = 0L,
    @NonNull val name: String,
    val picture: String?,
//    @ColumnInfo(name = "list_of_symbols") val listOfSymbols: ListOfSymbolsForDataBase,
    @NonNull val category: CategoryEnum

): Parcelable

@Entity
data class CardsAndSymbols(
    @PrimaryKey(autoGenerate = true) val pairId:Long = 0L,
    @NonNull val cardId: Long,
    @NonNull val symbol: SymbolForWashingDBO
)



