package com.blabla.dontruinyourlaundry.presentation.viewModels

import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.data.dataBase.CardsDao
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum

class ClothingCardsViewModel(private val repo: Repository) : ViewModel() {

    fun allCardsByCategory(category: CategoryEnum): LiveData<List<Card>> {
        return repo.allCardsByCategory(category).asLiveData()
    }
}
