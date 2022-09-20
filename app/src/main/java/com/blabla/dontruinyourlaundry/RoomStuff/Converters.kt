package com.blabla.dontruinyourlaundry.RoomStuff

import androidx.room.TypeConverter
import com.blabla.dontruinyourlaundry.data.ListOfSymboldForDataBase
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromSymbolListToJSON(list: ListOfSymboldForDataBase): String {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJSONToSymbolList(json: String): ListOfSymboldForDataBase {
        return Gson().fromJson(json,ListOfSymboldForDataBase::class.java)
    }
}