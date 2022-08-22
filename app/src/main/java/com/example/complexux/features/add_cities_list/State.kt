package com.example.complexux.features.add_cities_list

interface State{
    val name: String
    val fullName: String
    val color: Int
    val filterText: String
    val cities: List<City>
    val selectedCities: List<City>
    val isSelectEnabled: Boolean
}