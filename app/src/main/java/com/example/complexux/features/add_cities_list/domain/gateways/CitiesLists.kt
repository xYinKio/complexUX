package com.example.complexux.features.add_cities_list.domain.gateways

import com.example.complexux.features.add_cities_list.domain.CitiesListInDomain

interface CitiesLists {
    fun add(citiesList: CitiesListInDomain)
}