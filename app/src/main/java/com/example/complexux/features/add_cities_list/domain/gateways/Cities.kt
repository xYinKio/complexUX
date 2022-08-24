package com.example.complexux.features.add_cities_list.domain.gateways

import com.example.complexux.features.add_cities_list.domain.City

interface Cities {
    suspend fun get() : List<City>
}