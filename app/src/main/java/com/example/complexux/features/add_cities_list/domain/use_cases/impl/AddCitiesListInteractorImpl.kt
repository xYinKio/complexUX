package com.example.complexux.features.add_cities_list.domain.use_cases.impl

import com.example.complexux.features.add_cities_list.domain.CitiesListInDomain
import com.example.complexux.features.add_cities_list.domain.CityInDomain
import com.example.complexux.features.add_cities_list.domain.gateways.Cities
import com.example.complexux.features.add_cities_list.domain.gateways.CitiesLists
import com.example.complexux.features.add_cities_list.domain.use_cases.AddCitiesListInteractor

class AddCitiesListInteractorImpl(
    private val cities: Cities,
    private val citiesLists: CitiesLists
) : AddCitiesListInteractor {

    override suspend fun add(citiesList: CitiesListInDomain) {
        citiesLists.add(citiesList)
    }

    override suspend fun getCities(): List<CityInDomain> {
        return cities.get()
    }
}