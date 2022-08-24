package com.example.complexux.features.add_cities_list.data

import com.example.complexux.features.add_cities_list.domain.CitiesList
import com.example.complexux.features.add_cities_list.domain.City
import com.example.complexux.features.add_cities_list.domain.gateways.Cities
import com.example.complexux.features.add_cities_list.domain.gateways.CitiesLists
import com.example.complexux.features.cities_storage.CitiesStorage

class Repository() : Cities, CitiesLists {

    override suspend fun get(): List<City> {
        return CitiesStorage.cities.map { City(it.name, it.date) }
    }

    override fun add(citiesList: CitiesList) {
        CitiesStorage.citiesLists.add(com.example.complexux.features.cities_storage.CitiesList(
            citiesList.name,
            citiesList.fullName,
            cities = citiesList.cities.map { com.example.complexux.features.cities_storage.City(
                it.name,
                it.date
            ) },
            color = citiesList.color
        ))
    }
}