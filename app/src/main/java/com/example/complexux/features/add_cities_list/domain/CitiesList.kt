package com.example.complexux.features.add_cities_list.domain

data class CitiesList(
    val name: String,
    val fullName: String,
    val cities: List<City>,
    val color: Int
)