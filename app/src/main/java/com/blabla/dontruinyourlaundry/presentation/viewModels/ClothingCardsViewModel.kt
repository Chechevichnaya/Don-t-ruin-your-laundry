package com.blabla.dontruinyourlaundry.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.data.database.Card
import com.blabla.dontruinyourlaundry.domain.entity.CategoryEnum

class ClothingCardsViewModel(private val repo: Repository) : ViewModel() {

    fun allCardsByCategory(category: CategoryEnum): LiveData<List<Card>> {
        return repo.allCardsByCategory(category).asLiveData()
    }
}
