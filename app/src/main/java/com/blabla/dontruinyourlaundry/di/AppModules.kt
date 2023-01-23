package com.blabla.dontruinyourlaundry.di

import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.dataBase.CardsDataBase
import com.blabla.dontruinyourlaundry.domain.useCases.*
import com.blabla.dontruinyourlaundry.presentation.viewModels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val daoModule = module {
    single { CardsDataBase.getDatabase(get()) }
    single { get<CardsDataBase>().cardsDao }
    single { get<CardsDataBase>().cardsAndSymbolsDao }
}

val viewModelModule = module {
    viewModel { AddDataToCardViewModel(get(), get(), get()) }
    viewModel { CardDetailViewModel(get(), get()) }
    viewModel { ChooseSymbolsViewModel(get()) }
    viewModel { ClothingCardsViewModel(get()) }
    viewModel { SearchByNameViewModel(get()) }
    viewModel { SearchByParametersViewModel(get(), get()) }
    viewModel { SearchByParametersResultViewModel(get()) }
    viewModel { SymbolGuideViewModel(get()) }
}

val repoModule = module {
    factory { Repository(get(), get(), get()) }
}

val useCasesModule = module {
    factory { ChooseSymbolsToCardUseCase(get()) }
    factory { ShowCardDetailUseCase(get()) }
    factory { SearchByNameUseCase(get()) }
    factory { SearchByParameterUseCase(get()) }
    factory { ChangeOldCardUseCase(get()) }
    factory { CreateNewCardUseCase(get()) }
    factory { SearchByParameterGetResultUseCase(get()) }
}

