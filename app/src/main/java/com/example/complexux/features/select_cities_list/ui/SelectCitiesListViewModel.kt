package com.example.complexux.features.select_cities_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SelectCitiesListViewModel : ViewModel() {

    private val data = State()
    private val _state = MutableStateFlow<Event>(Event.Updated(data))
    val flow = _state.asStateFlow()

    init {
        data.citiesLists = listOf(
            CitiesList(
                name = "Europe 1",
                fullName = "Some full name 1",
                cities = listOf(
                    City(
                        name = "City 1",
                        date = "date 1"
                    ),
                    City(
                        name = "City 2",
                        date = "date 2"
                    ),
                    City(
                        name = "City 3",
                        date = "date 3"
                    ),
                    City(
                        name = "City 4",
                        date = "date 4"
                    ),
                    City(
                        name = "City 5",
                        date = "date 5"
                    ),
                ),
                color = "#FF121212"
            ),
            CitiesList(
                name = "Europe 2",
                fullName = "Some full name 2",
                cities = listOf(
                    City(
                        name = "City 1",
                        date = "date 1"
                    ),
                    City(
                        name = "City 2",
                        date = "date 2"
                    ),
                    City(
                        name = "City 3",
                        date = "date 3"
                    ),
                    City(
                        name = "City 4",
                        date = "date 4"
                    ),
                    City(
                        name = "City 5",
                        date = "date 5"
                    ),
                ),
                color = "#FF121212"
            ),
            CitiesList(
                name = "Europe 3",
                fullName = "Some full name 3",
                cities = listOf(
                    City(
                        name = "City 1",
                        date = "date 1"
                    ),
                    City(
                        name = "City 2",
                        date = "date 2"
                    ),
                    City(
                        name = "City 3",
                        date = "date 3"
                    ),
                    City(
                        name = "City 4",
                        date = "date 4"
                    ),
                    City(
                        name = "City 5",
                        date = "date 5"
                    ),
                ),
                color = "#FF121212"
            ),
            CitiesList(
                name = "Europe 4",
                fullName = "Some full name 4",
                cities = listOf(
                    City(
                        name = "City 1",
                        date = "date 1"
                    ),
                    City(
                        name = "City 2",
                        date = "date 2"
                    ),
                    City(
                        name = "City 3",
                        date = "date 3"
                    ),
                    City(
                        name = "City 4",
                        date = "date 4"
                    ),
                    City(
                        name = "City 5",
                        date = "date 5"
                    ),
                ),
                color = "#FF121212"
            ),
        )
        data.currentListFullName = data.citiesLists[0].name
        _state.value = Event.Updated(data)
    }

    fun obtainIntention(intention: Intention){
        viewModelScope.launch(Dispatchers.IO) {
            when(intention){
                is Intention.SelectList -> {
                    if (intention.index >= data.citiesLists.size) return@launch
                    data.currentListFullName = data.citiesLists[intention.index].fullName
                    _state.value = Event.ListSelected(data)
                }
                is Intention.StartDragAndDrop -> {
                    _state.value = Event.DragAndDropStarted(data)
                }
            }
        }

    }

    private data class State(
        override var currentListName: String = "",
        override var currentListFullName: String = "",
        override var citiesLists: List<CitiesList> = listOf()
    ) : Event.State
}