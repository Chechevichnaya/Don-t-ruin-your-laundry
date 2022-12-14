package com.blabla.dontruinyourlaundry.entity

sealed class SearchScreenItem {

    data class Title(val name: String) : SearchScreenItem()

    data class SearchParameter(
        val name: String,
        val selected: Boolean = false,
        val titleName: String
    ) : SearchScreenItem() {

        fun getCategory(): CategoryEnum {
             return when(this.name){
                CategoryEnum.CLOTH.getNameWithoutContext() -> CategoryEnum.CLOTH
                CategoryEnum.BAD_SHEETS.getNameWithoutContext() -> CategoryEnum.BAD_SHEETS
                CategoryEnum.KITCHEN.getNameWithoutContext() -> CategoryEnum.KITCHEN
                CategoryEnum.BATH.getNameWithoutContext() -> CategoryEnum.BATH
                else -> CategoryEnum.REST
            }
        }
    }

}



// ButtonsForSearching("Одежда"),
//            ButtonsForSearching("Постельное белье"),
//            ButtonsForSearching("Вещи из ванной"),
//            ButtonsForSearching("Вещи из кухни"),
//            ButtonsForSearching("Остальное")