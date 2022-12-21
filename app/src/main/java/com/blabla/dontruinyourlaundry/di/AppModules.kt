package com.blabla.dontruinyourlaundry.di

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.dataBase.CardsAndSymbolsDataBase
import com.blabla.dontruinyourlaundry.data.dataBase.CardsDataBase
import com.blabla.dontruinyourlaundry.domain.useCases.ChooseSymbolsToCardUC
import com.blabla.dontruinyourlaundry.presentation.viewModels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val daoModule = module {
    single { CardsDataBase.getDatabase(get()).cardsDao }
    single { CardsAndSymbolsDataBase.getDataBase(get()).cardsAndSymbolsDao }
}

val viewModelModule = module {
    viewModel { AddedCardsViewModel(get()) }
    viewModel { CardDetailViewModel(get()) }
    viewModel { ChooseSymbolsViewModel(get()) }
    viewModel { ClothingCardsViewModel(get()) }
    viewModel { SearchByNameViewModel(get()) }
    viewModel { SearchByParametersViewModel(get()) }
    viewModel { SearchByParametersResultViewModel(get()) }
    viewModel { SymbolGuideViewModel() }
}

val repoModule = module {
    factory { Repository(get(), get(), get()) }
}

val useCasesModule = module {
    factory { ChooseSymbolsToCardUC(get()) }
}

