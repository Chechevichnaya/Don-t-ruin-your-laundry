package com.blabla.dontruinyourlaundry.viewModels

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.ListOfCards
import com.blabla.dontruinyourlaundry.entity.SymbolGuide

class SymbolGuideViewModel : ViewModel() {

    private val _selectedSymbols = MutableLiveData<List<SymbolGuide>>()
    val selectedSymbols: LiveData<List<SymbolGuide>> = _selectedSymbols

    fun giveContextToViewModel(context: Context) {
        _selectedSymbols.value = ListOfCards.loadListOfSymbolGuide(context)
        Log.d("GUIDE","in view model ${_selectedSymbols.value!!.size}")
    }

    fun onClicked(item: SymbolGuide.SymbolForWashing, context: Context) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val dialog: AlertDialog = builder.setMessage(item.meaningOfSymbol)
            .setPositiveButton("Ok") { _, _ -> }
            .create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(context.resources.getColor(R.color.lilac_700))
    }
}
