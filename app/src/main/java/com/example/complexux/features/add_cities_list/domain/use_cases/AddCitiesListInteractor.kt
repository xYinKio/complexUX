package com.example.complexux.features.add_cities_list.domain.use_cases

import com.example.complexux.features.add_cities_list.domain.CitiesList
import com.example.complexux.features.add_cities_list.domain.City

interface AddCitiesListInteractor{
    suspend fun add(citiesList: CitiesList)
    suspend fun getCities() : List<City>
}