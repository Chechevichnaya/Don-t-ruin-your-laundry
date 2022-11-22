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

//class TspDeserializer : JsonDeserializer<TspEnum> {
//    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): TspEnum {
//        val stringValue = json.asString
//        for (enum in TspEnum.values()) {
//            if (enum.provider == stringValue) {
//                return enum
//            }
//        }
//        throw IllegalArgumentException("Unknown tsp $stringValue!")
//    }
//}
//val gson = GsonBuilder()
//    .registerTypeAdapter(TspEnum::class.java, TspDeserializer())
//    .create()

//val user = gson.fromJson(json, User::class.java)
//println(user) // prints User(tsp=AY_BEE_CEE, userId=lkajsdlk-199191-lkjdflakj)
