package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.dataBase.Card
import com.blabla.dontruinyourlaundry.domain.entity.SearchParameterEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchScreenItem
import com.blabla.dontruinyourlaundry.domain.useCases.SearchByParameterUseCase
import kotlinx.coroutines.launch

class SearchByParametersViewModel(
    private val useCase: SearchByParameterUseCase,
    private val context: Context
) :
    ViewModel() {


    private val _searchItems = MutableLiveData(useCase.getSearchItems())
    val searchItems: LiveData<List<SearchScreenItem>> = _searchItems

    private var selectedItems = listOf<SearchScreenItem>()


    var listOfSearchParametersEnum = listOf<SearchParameterEnum>()


//    private val _listOfCardsResult = MutableLiveData<List<Card>>()
    private var listOfCardsResult = listOf<Card>()

    private fun getSelectedItemsNames() {
        listOfSearchParametersEnum = useCase.getSelectedItemsNames(selectedItems)
    }

    fun onItemClicked(clickedItem: SearchScreenItem.SearchParameter) {
        _searchItems.value = useCase.onItemClicked(clickedItem, _searchItems.value.orEmpty())
    }

    fun processSelectedItems() {
        getSelectedItemsNames()
    }

    private fun getSelectedItems() {
        selectedItems =
            useCase.getSelectedItems(_searchItems.value.orEmpty().toMutableList())
    }

    fun checkIfItemsSelected(): Boolean {
        getSelectedItems()
        return selectedItems.isNotEmpty()
    }
//
//    private fun getListOfCards() {
////        val listOfSelectedParameters = _listOfSearchParametersEnum.value.orEmpty()
//        viewModelScope.launch {
//            _listOfCardsResult.value = useCase.getCardsSearchByParameter(listOfSearchParametersEnum)
//        }
//    }

    private fun renewSearchItems() {
        _searchItems.value = useCase.getSearchItems()
    }

    fun resultNotNull(context: Context, doOnComplete: () -> Unit) {
        getSelectedItemsNames()
        viewModelScope.launch {
            listOfCardsResult = useCase.getCardsSearchByParameter(listOfSearchParametersEnum)
            if (listOfCardsResult.isEmpty()) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                val dialog: AlertDialog =
                    builder.setMessage(context.getString(R.string.no_result))
                        .setPositiveButton(context.getString(R.string.ok_button)) { _, _ -> renewSearchItems() }
                        .create()
                dialog.show()
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(
                        ResourcesCompat.getColor(
                            context.resources,
                            R.color.buttons_positive_negative,
                            null
                        )
                    )

            } else {
                doOnComplete.invoke()
            }
        }
//        val listOfCardsResult = _listOfCardsResult.value.orEmpty()
//        Log.d("GAGAGA", "getListOfCards() - ${listOfCardsResult}")

    }
}
