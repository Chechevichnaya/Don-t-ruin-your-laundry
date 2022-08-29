package com.blabla.dontruinyourlaundry.dialogs

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle

import androidx.fragment.app.DialogFragment



class HowToUseAppDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(com.blabla.dontruinyourlaundry.R.string.help_with_app))
            .setMessage(getString(com.blabla.dontruinyourlaundry.R.string.helper_text))
            .setPositiveButton(getString(com.blabla.dontruinyourlaundry.R.string.ok_button)) { _, _ -> }
            .create()


    companion object {
        const val TAG = "Help_With_App_Dialog"
    }



}