package com.example.complexux.features.select_cities_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.complexux.features.select_cities_list.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SelectCitiesListViewModel : ViewModel() {

    private val state = State()
    private val _flow = MutableStateFlow<Event>(Event.Updated(state))
    val flow = _flow.asStateFlow()

    init {
        val repo = Repository()
        state.citiesLists = repo.getCitiesLists()
        state.currentListFullName = state.citiesLists[0].name
        _flow.value = Event.Updated(state)
    }

    fun obtainIntention(intention: Intention){
        viewModelScope.launch(Dispatchers.IO) {
            when(intention){
                is Intention.SelectList -> {
                    if (intention.index >= state.citiesLists.size) return@launch
                    state.currentListFullName = state.citiesLists[intention.index].fullName
                    _flow.value = Event.ListSelected(state)
                }
                is Intention.StartDragAndDrop -> {
                    _flow.value = Event.DragAndDropStarted(state)
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