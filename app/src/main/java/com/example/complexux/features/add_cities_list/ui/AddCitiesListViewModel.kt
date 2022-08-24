package com.example.complexux.features.add_cities_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.complexux.features.add_cities_list.domain.CitiesListInDomain
import com.example.complexux.features.add_cities_list.domain.CityInDomain
import com.example.complexux.features.add_cities_list.domain.use_cases.AddCitiesListInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MAX_CITIES_COUNT = 5

class AddCitiesListViewModel(
    private val interactor: AddCitiesListInteractor
) : ViewModel() {

    private val _flow = MutableStateFlow<Event>(Event.Init(StateImpl()))
    val flow = _flow.asStateFlow()

    private val state = StateImpl()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            state.cities = getCities()
            _flow.value = Event.Updated(state)
        }
    }

    private suspend fun getCities() = withContext(Dispatchers.IO){
        interactor.getCities().map { CityImpl(it.name, it.date, false) }
    }


    fun obtainIntention(intention: Intention){
        viewModelScope.launch(Dispatchers.Default) {
            when(intention){
                Intention.Complete -> {
                    var canWrite = false
                    when{
                        state.selectedCities.isEmpty() -> _flow.value = Event.ErrorNoSelected(state)
                        state.name.isEmpty() -> _flow.value = Event.ErrorEmptyName(state)
                        state.fullName.isEmpty() -> _flow.value = Event.ErrorEmptyFullName(state)
                        else -> {canWrite = true}
                    }

                    if (!canWrite) return@launch

                    withContext(Dispatchers.IO){
                        interactor.add(
                            CitiesListInDomain(
                                name = state.name,
                                fullName = state.fullName,
                                cities = state.selectedCities.map { CityInDomain(
                                    it.name,
                                    it.date
                                ) },
                                color = state.color
                            )
                        )
                    }
                    _flow.value = Event.Success(state)

                }
                is Intention.Filter -> {
                    val filtered = getCities().filter { it.name.uppercase().contains(intention.text.uppercase()) }
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
                    state.selectedCities.add(intention.city)
                    state.isSelectEnabled = state.selectedCities.size <= MAX_CITIES_COUNT - 1
                    _flow.value = Event.CitySelected(state)
                }
                is Intention.UnselectCity -> {
                    intention.city as CityImpl
                    intention.city.isSelected = false
                    state.selectedCities.remove(intention.city)
                    state.isSelectEnabled = state.selectedCities.size <= MAX_CITIES_COUNT - 1
                    _flow.value = Event.CityUnselected(state)
                }
            }
        }


    }

    private data class CityImpl(
        override val name: String,
        override val date: String,
        override var isSelected: Boolean,
    ) : City

    private data class StateImpl(
        override var name: String = "",
        override var fullName: String = "",
        override var color: Int = 0xFF0000,
        override var filterText: String = "",
        override var cities: List<CityImpl> = listOf(),
        override var selectedCities: MutableList<CityImpl> = mutableListOf(),
        override var isSelectEnabled: Boolean = true,
    ) : State

    class Factory(
        private val addCitiesListInteractor: AddCitiesListInteractor
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddCitiesListViewModel(addCitiesListInteractor) as T
        }
    }
}