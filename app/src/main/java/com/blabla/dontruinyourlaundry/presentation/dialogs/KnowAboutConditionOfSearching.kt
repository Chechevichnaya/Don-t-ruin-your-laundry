package com.blabla.dontruinyourlaundry.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.blabla.dontruinyourlaundry.R

class KnowAboutConditionOfSearching: DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myView = LayoutInflater.from(context).inflate(R.layout.search_by_parameter_dialog, null)

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val dialog: AlertDialog = builder.setTitle("Условия поиска")
            .setView(myView)
            .setPositiveButton(getString(R.string.ok_button)) {_, _ ->}
            .create()
        dialog.show()
        val colorButton = ResourcesCompat.getColor(
            requireContext().resources,
            R.color.lilac_700,
            null
        )
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorButton)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(colorButton)
        return dialog

    }

}
