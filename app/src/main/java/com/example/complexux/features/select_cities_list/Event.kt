package com.example.complexux.features.select_cities_list

sealed interface Event {
    val state: State
    class ListSelected(override val state: State) : Event
    class Updated(override val state: State) : Event
    class DragAndDropStarted(override val state: State) : Event

    interface State{
        val currentListName: String
        val currentListFullName: String
        val citiesLists: List<CitiesList>
    }
}