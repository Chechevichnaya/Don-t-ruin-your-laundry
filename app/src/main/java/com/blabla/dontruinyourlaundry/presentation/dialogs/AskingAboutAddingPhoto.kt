package com.blabla.dontruinyourlaundry.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.blabla.dontruinyourlaundry.R

class AskingAboutAddingPhoto: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val dialog: AlertDialog = builder.setMessage(getString(com.blabla.dontruinyourlaundry.R.string.ask_about_how_to_add_photo))
            .setPositiveButton(getString(com.blabla.dontruinyourlaundry.R.string.from_gallery)) { _, _ -> }
            .setNegativeButton(getString(com.blabla.dontruinyourlaundry.R.string.from_camera)) { _, _ -> }
            .create()
        dialog.show()
        val colorButton = resources.getColor(R.color.lilac_700)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorButton)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(colorButton)
        return dialog

    }

}
