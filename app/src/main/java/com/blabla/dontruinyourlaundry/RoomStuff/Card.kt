package com.blabla.dontruinyourlaundry.RoomStuff

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blabla.dontruinyourlaundry.data.ListOfSymbolsForDataBase
import com.blabla.dontruinyourlaundry.entity.Category
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Card(
    @PrimaryKey (autoGenerate = true) val id: Long = 0L,
    val name: String,
    val picture: String?,
    @ColumnInfo(name = "list_of_symbols") val listOfSymbols: ListOfSymbolsForDataBase,
    val category: Category

): Parcelable