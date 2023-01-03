package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.domain.entity.SearchParameterEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchScreenItem
import com.blabla.dontruinyourlaundry.domain.useCases.SearchByParameterGetResultUseCase
import kotlinx.coroutines.launch

class SearchByParametersResultViewModel(
    private val useCase: SearchByParameterGetResultUseCase,
    private val context: Context
) :
    ViewModel() {


    private val _searchItems = MutableLiveData(useCase.getSearchItems())
//    val selectedItems: LiveData<List<SearchScreenItem>> = _selectedItems

    private val _listOfSelectedParameters = MutableLiveData<List<SearchParameterEnum>>()

    private val _listOfSearchItemsOnlyWithSelectedItems = MutableLiveData<List<SearchScreenItem>>()

    private val _listOfCardsResult = MutableLiveData<List<Card>>()


    fun setListOfSelectedParameters(list: List<SearchParameterEnum>) {
        _listOfSelectedParameters.value = list
    }


    fun getListOfCards(): LiveData<List<Card>> {
        val listOfSelectedParameters = _listOfSelectedParameters.value.orEmpty()
        viewModelScope.launch {
            _listOfCardsResult.value = useCase.getCardsSearchByParameter(listOfSelectedParameters)
        }
        return _listOfCardsResult
    }

    fun getListSearchItemsWithSelectedItems(): LiveData<List<SearchScreenItem>> {
        val searchItems = _searchItems.value.orEmpty().toMutableList()
        val selectedItems = _listOfSelectedParameters.value.orEmpty().toMutableList()
        val listOfTitles = selectedItems.map { it.getTitle() }
        Log.d("FINISH", "listOfNames $listOfTitles")
        searchItems.forEach {
            if (it is SearchScreenItem.Title) {
                Log.d("FINISH", "it = ${it.name.getTitleName(context)}")
            }
        }


        val listForRecyclerView = searchItems.filter {
            (it is SearchScreenItem.SearchParameter && selectedItems.contains(
                it.name
            )) || (it is SearchScreenItem.Title && listOfTitles.contains(it))
        }
        _listOfSearchItemsOnlyWithSelectedItems.value = listForRecyclerView.map { item ->
            if (item is SearchScreenItem.SearchParameter) {
                item.copy(selected = true)
            } else {
                item
            }
        }
        return _listOfSearchItemsOnlyWithSelectedItems


    }

}