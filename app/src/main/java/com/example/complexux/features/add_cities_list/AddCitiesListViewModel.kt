package com.example.complexux.features.add_cities_list

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddCitiesListViewModel : ViewModel() {

    private val _flow = MutableStateFlow<Event>(Event.Init(State()))
    val flow = _flow.asStateFlow()

    private val state = State()

    private val allCities = listOf(
        CityImpl("name1", "date", false),
        CityImpl("name2", "date", false),
        CityImpl("name3", "date", false),
        CityImpl("name4", "date", false),
        CityImpl("name5", "date", false),
        CityImpl("name6", "date", false),
        CityImpl("name7", "date", false),
        CityImpl("name8", "date", false),
        CityImpl("name9", "date", false),
        CityImpl("name10", "date", false),
        CityImpl("name11", "date", false),
        CityImpl("name12", "date", false),
        CityImpl("name13", "date", false),
        CityImpl("name14", "date", false),
        CityImpl("name15", "date", false),
        CityImpl("name16", "date", false),
        CityImpl("name17", "date", false),
        CityImpl("name18", "date", false),
        CityImpl("name19", "date", false),
        CityImpl("name20", "date", false),
    )

    init {
        state.cities = allCities
        _flow.value = Event.Updated(state)

    }

    fun obtainIntention(intention: Intention){

        when(intention){
            Intention.Complete -> {/*TODO*/}
            is Intention.Filter -> {
                val filtered = allCities.filter { it.name.uppercase().contains(intention.text.uppercase()) }
                state.cities = filtered
                _flow.value = Event.Filtered(state)
            }
            is Intention.SelectColor -> {
                state.color = intention.color
                _flow.value = Event.ColorSelected(state)
            }
            is Intention.TypeFullName -> {
                state.fullName = intention.text
                _flow.value = Event.FullNameTyped(state)
            }
            is Intention.TypeName -> {
                state.name = intention.text
                _flow.value = Event.NameTyped(state)
            }
            is Intention.SelectCity -> {
                intention.city as CityImpl
                intention.city.isSelected = true
                Log.d("!!!", "${intention.city.isSelected}")
                state.selectedCities.add(intention.city)
                _flow.value = Event.CitySelected(state)
            }
            is Intention.UnselectCity -> {
                intention.city as CityImpl
                intention.city.isSelected = false
                state.selectedCities.remove(intention.city)
                _flow.value = Event.CityUnselected(state)
            }
        }

    }

    private data class CityImpl(
        override val name: String,
        override val date: String,
        override var isSelected: Boolean
    ) : City

    private data class State(
        override var name: String = "",
        override var fullName: String = "",
        override var color: Int = 0xFF0000,
        override var filterText: String = "",
        override var cities: List<CityImpl> = listOf(),
        override var selectedCities: MutableList<CityImpl> = mutableListOf(),
    ) : Event.State
}