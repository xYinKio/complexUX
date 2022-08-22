package com.example.complexux.features.select_cities_list

sealed interface State {
    class ListSelected(val data: Data) : State
    class Updated(val data: Data) : State

    interface Data{
        val currentListFullName: String
        val citiesLists: List<CitiesList>
    }
}