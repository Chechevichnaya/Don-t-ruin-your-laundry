package com.blabla.dontruinyourlaundry.entity

sealed class SearchScreenItem {

    data class Title(val name: String) : SearchScreenItem()

    data class SearchParameter(
        val name: String,
        val selected: Boolean = false,
        val titleName: String
    ) : SearchScreenItem()

}