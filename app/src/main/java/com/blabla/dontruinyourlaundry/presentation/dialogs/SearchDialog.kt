package com.blabla.dontruinyourlaundry.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.blabla.dontruinyourlaundry.R

const val BY_NAME = 0
const val BY_PARAMETERS = 1

class SearchDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val array = arrayOf(
            requireContext().getString(R.string.by_name),
            requireContext().getString(R.string.by_parameters)
        )
        var checkedItem = -1
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
        builder.setTitle(requireContext().getString(R.string.search_the_items))
            .setSingleChoiceItems(array, checkedItem)
            { _, which -> checkedItem = which }
            .setPositiveButton(requireContext().getString(R.string.ok_button)) { _, _ ->
                if (checkedItem != -1) {
                    when (checkedItem) {
                        BY_NAME -> {
                            findNavController().navigate(R.id.action_searchDialog_to_searchFragment)
                        }
                        BY_PARAMETERS -> {
                            findNavController().navigate(R.id.action_searchDialog_to_searchByParametersFragment)
                        }
                    }
                }
            }
            .setNeutralButton(requireContext().getString(R.string.cancel)) { _, _ -> findNavController().popBackStack() }
        val dialog = builder.create()
        dialog.show()

        val colorButton = ResourcesCompat.getColor(
            requireContext().resources,
            R.color.buttons_positive_negative,
            null
        )
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorButton)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(colorButton)

        dialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                requireContext().resources,
                R.drawable.color_for_alertdialog,
                null
            )
        )
        return dialog
    }

}