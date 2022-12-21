package com.blabla.dontruinyourlaundry.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MeaningOfSymbol: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(com.blabla.dontruinyourlaundry.R.string.helper_text))
            .setPositiveButton(getString(com.blabla.dontruinyourlaundry.R.string.ok_button)) { _, _ -> }
            .create()

}