package com.blabla.dontruinyourlaundry.data

import com.blabla.dontruinyourlaundry.entity.ButtonsForSearching

data class SearchByParametersCard(
    val title: String,
    val listOfButton:List<ButtonsForSearching>,
    val selectionType: SelectionType
)

enum class SelectionType {
    SINGLE,
    MULTI
}