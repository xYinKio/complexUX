package com.example.complexux.features.select_cities_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectCitiesListViewModel : ViewModel() {

    private val data = Data()
    private val _state = MutableStateFlow<State>(State.Updated(data))
    val state = _state.asStateFlow()

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
        _state.value = State.Updated(data)
    }

    fun obtainIntention(intention: Intention){
        when(intention){
            is Intention.SelectList -> {
                data.currentListFullName = data.citiesLists[intention.index].fullName
                _state.value = State.ListSelected(data)
            }
        }
    }

    private data class Data(
        override var currentListFullName: String = "",
        override var citiesLists: List<CitiesList> = listOf()
    ) : State.Data
}