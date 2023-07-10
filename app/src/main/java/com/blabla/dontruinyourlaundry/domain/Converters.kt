package com.blabla.dontruinyourlaundry.domain

import androidx.room.TypeConverter
import com.blabla.dontruinyourlaundry.domain.entity.SelectedSymbolsDBO
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromSymbolListToJSON(list: SelectedSymbolsDBO): String {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromJSONToSymbolList(json: String): SelectedSymbolsDBO {
        return Gson().fromJson(json, SelectedSymbolsDBO::class.java)
    }
}


