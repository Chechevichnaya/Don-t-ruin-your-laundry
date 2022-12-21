package com.blabla.dontruinyourlaundry.data

import android.app.Application
import com.blabla.dontruinyourlaundry.di.daoModule
import com.blabla.dontruinyourlaundry.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(daoModule)
            modules(viewModelModule)
        }
    }


//    val dataBase: CardsDataBase by lazy { CardsDataBase.getDatabase(this) }
}

