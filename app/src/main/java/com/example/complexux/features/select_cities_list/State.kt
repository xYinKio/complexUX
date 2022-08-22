package com.example.complexux.features.select_cities_list

sealed interface State {
    class ListSelected(val currentListFullName: String) : State
    class Updated(val data: Data) : State
    class DragAndDropStarted(val citiesListName: String) : State

    interface Data{
        val currentListFullName: String
        val citiesLists: List<CitiesList>
    }
}