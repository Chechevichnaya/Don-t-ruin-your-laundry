package com.blabla.dontruinyourlaundry.data

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


    fun getData(): List<SearchByParametersCards> {
        return listOf(
            SearchByParametersCards(
                title = "Категории",
                listOfButton = getListForCategorySearch(),
                selectionType = SelectionType.MULTI
            ),
            SearchByParametersCards(
                title = "Стирка",
                listOfButton = getListForLaundrySearch(),
                selectionType = SelectionType.SINGLE
            ),
            SearchByParametersCards(
                title = "Режим стирки",
                listOfButton = getListForLaundryRegimeSearch(),
                selectionType = SelectionType.MULTI
            ),
            SearchByParametersCards(
                title = "Сушка в сушильной машине",
                listOfButton = getListForDryingSearch(),
                selectionType = SelectionType.SINGLE
            ),
            SearchByParametersCards(
                title = "Глажка",
                listOfButton = getListForIroningSearch(),
                selectionType = SelectionType.SINGLE
            )
        )
    }
}
