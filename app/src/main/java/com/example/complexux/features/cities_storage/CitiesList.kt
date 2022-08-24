package com.example.complexux.features.cities_storage

data class CitiesList(
    val name: String,
    val fullName: String,
    val cities: List<City>,
    val color: Int
)