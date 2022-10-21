package com.blabla.dontruinyourlaundry.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.blabla.dontruinyourlaundry.R

const val BY_NAME = 0
const val BY_PARAMETERS = 1

class SearchDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val array = arrayOf("По названию", "По параметрам")
        var checkedItem = -1
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Поиск вещицы")
            .setSingleChoiceItems(array, checkedItem)
            { _, which -> checkedItem = which }
            .setPositiveButton("Ok") { _, _ ->
                if (checkedItem != -1) {
                    when (checkedItem) {
                        BY_NAME -> {
                            findNavController().navigate(R.id.action_searchDialog_to_searchFragment)
                        }
                        BY_PARAMETERS -> {}
                    }
                }
            }
            .setNeutralButton("Отмена") { _, _ -> findNavController().popBackStack() }
        val dialog = builder.create()
        dialog.show()
        return dialog

    }

}