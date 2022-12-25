package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuide

class SymbolGuideViewModel(private val repo: Repository) :
    ViewModel() {

    private val _symbolsInGuide = MutableLiveData(repo.getSymbolGuideList())
    val symbolsInGuide: LiveData<List<SymbolGuide>> = _symbolsInGuide

    fun onClicked(clickedItem: SymbolGuide.SymbolForWashing, context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val dialog: AlertDialog = builder
            .setTitle(context.getString(R.string.symbol))
            .setMessage(clickedItem.meaningOfSymbol)
            .setPositiveButton("Ok") { _, _ ->  }
            .setIcon(clickedItem.pictureId)
            .create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(context.resources.getColor(R.color.lilac_700))
    }

}
