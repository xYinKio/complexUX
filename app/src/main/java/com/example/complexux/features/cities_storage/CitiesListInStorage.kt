package com.example.complexux.features.cities_storage

data class CitiesListInStorage(
    val name: String,
    val fullName: String,
    val cities: List<CityInStorage>,
    val color: Int
)