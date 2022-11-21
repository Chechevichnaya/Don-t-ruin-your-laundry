package com.blabla.dontruinyourlaundry.data

import com.blabla.dontruinyourlaundry.entity.ButtonsForSearching

object DataForSearchByParameters {

    private fun getListForCategorySearch(): List<ButtonsForSearching> {
        return listOf(
            ButtonsForSearching("Одежда"),
            ButtonsForSearching("Постельное белье"),
            ButtonsForSearching("Вещи из ванной"),
            ButtonsForSearching("Вещи из кухни"),
            ButtonsForSearching("Остальное")
        )
    }

     fun getListForLaundrySearch(): List<ButtonsForSearching> {
        return listOf(
            ButtonsForSearching("Разрешена"),
            ButtonsForSearching("Запрещена"),
            ButtonsForSearching("Только ручная")
        )
    }

    private fun getListForLaundryRegimeSearch(): List<ButtonsForSearching> {
        return listOf(
            ButtonsForSearching("30"),
            ButtonsForSearching("40"),
            ButtonsForSearching("50"),
            ButtonsForSearching("60"),
            ButtonsForSearching("70"),
            ButtonsForSearching("95")
        )
    }

     fun getListForDryingSearch(): List<ButtonsForSearching> {
        return listOf(
            ButtonsForSearching("Разрешена"),
            ButtonsForSearching("Запрещена"),
        )
    }

     fun getListForIroningSearch(): List<ButtonsForSearching> {
        return listOf(
            ButtonsForSearching("Разрешена"),
            ButtonsForSearching("Запрещена"),
        )
    }


    fun getData(): List<SearchByParametersCard> {
        return listOf(
            SearchByParametersCard(
                title = "Категории",
                listOfButton = getListForCategorySearch(),
                selectionType = SelectionType.MULTI
            ),
            SearchByParametersCard(
                title = "Стирка",
                listOfButton = getListForLaundrySearch(),
                selectionType = SelectionType.SINGLE
            ),
            SearchByParametersCard(
                title = "Режим стирки",
                listOfButton = getListForLaundryRegimeSearch(),
                selectionType = SelectionType.MULTI
            ),
            SearchByParametersCard(
                title = "Сушка в сушильной машине",
                listOfButton = getListForDryingSearch(),
                selectionType = SelectionType.SINGLE
            ),
            SearchByParametersCard(
                title = "Глажка",
                listOfButton = getListForIroningSearch(),
                selectionType = SelectionType.SINGLE
            )
        )
    }
}
