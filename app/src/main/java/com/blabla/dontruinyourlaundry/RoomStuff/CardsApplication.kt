package com.blabla.dontruinyourlaundry.RoomStuff

import android.app.Application

class CardsApplication:Application() {
    val dataBase: CardsDataBase by lazy { CardsDataBase.getDatabase(this) }
}

