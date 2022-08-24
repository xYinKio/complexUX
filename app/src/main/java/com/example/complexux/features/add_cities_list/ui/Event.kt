package com.example.complexux.features.add_cities_list.ui

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
    class ErrorNoSelected(override val state: State) : Event
    class ErrorEmptyName(override val state: State) : Event
    class ErrorEmptyFullName(override val state: State) : Event
    class Success(override val state: State) : Event
}