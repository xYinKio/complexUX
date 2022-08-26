package com.example.complexux.features.selected_cities_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.complexux.features.cities_storage.CitiesStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class SelectedCitiesListViewModel : ViewModel() {

    private val _flow = MutableStateFlow<Event>(Event.Init)
    val flow = _flow.asStateFlow()

    private val state = StateImpl()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            state.cities = CitiesStorage.citiesLists[0].cities.map { City(it.name, it.date) }
            _flow.value = Event.Updated(state)
        }
    }

    fun selectCity(name: String){
        state.cities = CitiesStorage.citiesLists.find { it.name.uppercase() == name.uppercase() }!!.cities.map { City(it.name, it.date) }
        _flow.value = Event.Updated(state)
    }

    fun swap(i: Int, j: Int){
        val cities = state.cities.toMutableList()
        Collections.swap(cities, i, j)
        state.cities = cities
    }


    private data class StateImpl(
        override var cities: List<City> = listOf(),
        override var color: Int = 0
    ): State
}