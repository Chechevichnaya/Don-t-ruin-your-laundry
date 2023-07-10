package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.app.AlertDialog
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.database.Card
import com.blabla.dontruinyourlaundry.domain.entity.SearchParameterEnum
import com.blabla.dontruinyourlaundry.domain.entity.SearchScreenItem
import com.blabla.dontruinyourlaundry.domain.useCases.SearchByParameterUseCase
import kotlinx.coroutines.launch

class SearchByParametersViewModel(
    private val useCase: SearchByParameterUseCase,
) : ViewModel() {

    private val _searchItems = MutableLiveData(useCase.getSearchItems())
    val searchItems: LiveData<List<SearchScreenItem>> = _searchItems

    private var selectedItems = listOf<SearchScreenItem>()

    var listOfSearchParametersEnum = listOf<SearchParameterEnum>()

    private var listOfCardsResult = listOf<Card>()

    private fun getSelectedItemsNames() {
        listOfSearchParametersEnum = useCase.getSelectedItemsNames(selectedItems)
    }

    fun onItemClicked(clickedItem: SearchScreenItem.SearchParameter) {
        _searchItems.value =
            useCase.getSelectedSearchItems(clickedItem, _searchItems.value.orEmpty())
    }

    private fun getSelectedItems() {
        selectedItems =
            useCase.getSelectedItems(_searchItems.value.orEmpty().toMutableList())
    }

    fun checkIfItemsSelected(): Boolean {
        getSelectedItems()
        return selectedItems.isNotEmpty()
    }

    private fun resetSearchItems() {
        _searchItems.value = useCase.getSearchItems()
    }

    fun handleSelectedParameters(context: Context, doOnComplete: () -> Unit) {
        getSelectedItemsNames()
        viewModelScope.launch {
            listOfCardsResult = useCase.getCardsSearchByParameter(listOfSearchParametersEnum)
            if (listOfCardsResult.isEmpty()) {
                showNothingFoundDialog(context)
            } else {
                doOnComplete.invoke()
            }
        }
    }

    private fun showNothingFoundDialog(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val dialog: AlertDialog =
            builder.setMessage(context.getString(R.string.no_result))
                .setPositiveButton(context.getString(R.string.ok_button)) { _, _ -> resetSearchItems() }
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
    }
}
