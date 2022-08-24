package com.example.complexux.features.add_cities_list.domain.use_cases.impl

import com.example.complexux.features.add_cities_list.domain.CitiesList
import com.example.complexux.features.add_cities_list.domain.City
import com.example.complexux.features.add_cities_list.domain.gateways.Cities
import com.example.complexux.features.add_cities_list.domain.gateways.CitiesLists
import com.example.complexux.features.add_cities_list.domain.use_cases.AddCitiesListInteractor

class AddCitiesListInteractorImpl(
    private val cities: Cities,
    private val citiesLists: CitiesLists
) : AddCitiesListInteractor {

    override suspend fun add(citiesList: CitiesList) {
        citiesLists.add(citiesList)
    }

    override suspend fun getCities(): List<City> {
        return cities.get()
    }
}