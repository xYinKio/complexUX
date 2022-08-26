package com.example.complexux.features.selected_cities_list.ui

sealed interface Event{
    object Init : Event
    class Updated(val state: State) : Event
}