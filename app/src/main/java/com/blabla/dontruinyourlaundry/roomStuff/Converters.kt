package com.blabla.dontruinyourlaundry.roomStuff

import androidx.room.TypeConverter
import com.blabla.dontruinyourlaundry.data.ListOfSymbolsForDataBase
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromSymbolListToJSON(list: ListOfSymbolsForDataBase): String {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJSONToSymbolList(json: String): ListOfSymbolsForDataBase {
        return Gson().fromJson(json,ListOfSymbolsForDataBase::class.java)
    }
}


