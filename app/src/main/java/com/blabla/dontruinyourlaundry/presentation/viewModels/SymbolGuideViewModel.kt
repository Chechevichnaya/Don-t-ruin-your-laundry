package com.blabla.dontruinyourlaundry.presentation.viewModels

import android.app.AlertDialog
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.Repository
import com.blabla.dontruinyourlaundry.domain.entity.SymbolGuideItem

class SymbolGuideViewModel(repo: Repository) : ViewModel() {

    private val _symbolsInGuide = MutableLiveData(repo.getSymbolGuideList())
    val symbolsInGuide: LiveData<List<SymbolGuideItem>> = _symbolsInGuide

    fun onClicked(clickedItem: SymbolGuideItem.SymbolForWashing, context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val drawable = ResourcesCompat.getDrawable(context.resources, clickedItem.pictureId, null)
        val wrappedDrawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(
            wrappedDrawable,
            ResourcesCompat.getColor(context.resources, R.color.icon_text, null)
        )
        val dialog: AlertDialog = builder
            .setTitle(context.getString(R.string.symbol))
            .setMessage(clickedItem.meaningOfSymbol)
            .setPositiveButton(context.getString(R.string.ok_button)) { _, _ -> }
            .setIcon(clickedItem.pictureId)
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
