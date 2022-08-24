package com.example.complexux.features.add_cities_list.domain

data class CitiesListInDomain(
    val name: String,
    val fullName: String,
    val cities: List<CityInDomain>,
    val color: Int
)