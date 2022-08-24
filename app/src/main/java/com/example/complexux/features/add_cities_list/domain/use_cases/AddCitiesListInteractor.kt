package com.example.complexux.features.add_cities_list.domain.use_cases

import com.example.complexux.features.add_cities_list.domain.CitiesListInDomain
import com.example.complexux.features.add_cities_list.domain.CityInDomain

interface AddCitiesListInteractor{
    suspend fun add(citiesList: CitiesListInDomain)
    suspend fun getCities() : List<CityInDomain>
}