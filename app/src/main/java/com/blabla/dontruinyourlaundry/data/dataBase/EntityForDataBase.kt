package com.blabla.dontruinyourlaundry.data.dataBase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum
import com.blabla.dontruinyourlaundry.domain.entity.SymbolForWashingDBO
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Card(
    @PrimaryKey (autoGenerate = true) val cardId: Long = 0L,
    val name: String,
    val picture: String?,
    val category: CategoryEnum

): Parcelable

@Entity
data class CardsAndSymbols(
    @PrimaryKey(autoGenerate = true) val pairId:Long = 0L,
    val cardAndSymbolId: Long,
    val symbol: SymbolForWashingDBO
)



