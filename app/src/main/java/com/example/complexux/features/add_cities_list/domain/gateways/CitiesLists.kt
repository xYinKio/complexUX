package com.example.complexux.features.add_cities_list.domain.gateways

import com.example.complexux.features.add_cities_list.domain.CitiesList

interface CitiesLists {
    fun add(citiesList: CitiesList)
}