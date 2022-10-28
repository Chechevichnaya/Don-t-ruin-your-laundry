package com.blabla.dontruinyourlaundry.data

data class SearchByParametersCards(
    val title: String,
    val listOfButton:List<ButtonsForSearching>,
    val selectionType: SelectionType
)

enum class SelectionType {
    SINGLE,
    MULTI
}