package com.blabla.dontruinyourlaundry.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AskingAboutAddingPhoto: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(com.blabla.dontruinyourlaundry.R.string.ask_about_how_to_add_photo))
            .setPositiveButton(getString(com.blabla.dontruinyourlaundry.R.string.from_gallery)) { _, _ -> }
            .setNegativeButton(getString(com.blabla.dontruinyourlaundry.R.string.from_camera)) { _, _ -> }
            .create()
}