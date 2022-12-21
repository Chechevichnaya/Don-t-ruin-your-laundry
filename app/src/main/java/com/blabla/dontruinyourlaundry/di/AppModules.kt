package com.blabla.dontruinyourlaundry.di

import com.blabla.dontruinyourlaundry.data.dataBase.CardsAndSymbolsDataBase
import com.blabla.dontruinyourlaundry.data.dataBase.CardsDataBase
import com.blabla.dontruinyourlaundry.presentation.viewModels.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val daoModule = module {
    single { CardsDataBase.getDatabase(get()).cardsDao }
    single { CardsAndSymbolsDataBase.getDataBase(androidContext()).cardsAndSymbolsDao }
}

val viewModelModule = module {
    viewModel { AddedCardsViewModel(get()) }
    viewModel { CardDetailViewModel(get()) }
    viewModel { ChooseSymbolsViewModel() }
    viewModel { ClothingCardsViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SearchByParametersViewModel(get()) }
    viewModel { SearchByParametersResultViewModel(get()) }
    viewModel { SymbolGuideViewModel() }
}

