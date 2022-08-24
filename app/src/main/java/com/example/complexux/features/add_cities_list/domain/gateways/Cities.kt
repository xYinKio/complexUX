package com.example.complexux.features.add_cities_list.domain.gateways

import com.example.complexux.features.add_cities_list.domain.CityInDomain

interface Cities {
    suspend fun get() : List<CityInDomain>
}