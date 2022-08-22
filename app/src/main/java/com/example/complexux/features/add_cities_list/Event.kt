package com.example.complexux.features.add_cities_list

sealed interface Event{
    val state: State
    class ColorSelected(override val state: State) : Event
    class NameTyped(override val state: State) : Event
    class FullNameTyped(override val state: State) : Event
    class Filtered(override val state: State) : Event
    class Init(override val state: State) : Event
    class CitySelected(override val state: State) : Event
    class CityUnselected(override val state: State) : Event
    class Updated(override val state: State) : Event

    interface State{
        val name: String
        val fullName: String
        val color: Int
        val filterText: String
        val cities: List<City>
        val selectedCities: List<City>
    }
}