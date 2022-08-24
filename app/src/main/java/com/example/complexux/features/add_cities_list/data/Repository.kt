package com.example.complexux.features.add_cities_list.data

import com.example.complexux.features.add_cities_list.domain.CitiesListInDomain
import com.example.complexux.features.add_cities_list.domain.CityInDomain
import com.example.complexux.features.add_cities_list.domain.gateways.Cities
import com.example.complexux.features.add_cities_list.domain.gateways.CitiesLists
import com.example.complexux.features.cities_storage.CitiesListInStorage
import com.example.complexux.features.cities_storage.CitiesStorage
import com.example.complexux.features.cities_storage.CityInStorage

class Repository() : Cities, CitiesLists {

    override suspend fun get(): List<CityInDomain> {
        return CitiesStorage.cities.map { CityInDomain(it.name, it.date) }
    }

    override fun add(citiesList: CitiesListInDomain) {
        CitiesStorage.citiesLists.add(
            CitiesListInStorage(
            citiesList.name,
            citiesList.fullName,
            cities = citiesList.cities.map { CityInStorage(
                it.name,
                it.date
            ) },
            color = citiesList.color
        )
        )
    }
}