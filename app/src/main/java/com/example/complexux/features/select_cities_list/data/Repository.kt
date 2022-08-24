package com.example.complexux.features.select_cities_list.data

import com.example.complexux.features.cities_storage.CitiesStorage
import com.example.complexux.features.select_cities_list.ui.CitiesList
import com.example.complexux.features.select_cities_list.ui.City

/*
* Здесь нарушается принцип DI. Это сделано осознанно для экономии времени, исключительно в
* рамках тестового задания. В пакете add_cities_list принципы SOLID не нарушаются.
* */
class Repository {
    fun getCitiesLists() : List<CitiesList>{
        return CitiesStorage.citiesLists.map {
            CitiesList(
                it.name,
                it.fullName,
                it.cities.map { City(it.name, it.date) },
                it.color
            )
        }
    }
}