package com.example.complexux.features.selected_cities_list.ui

interface State {
    val cities: List<City>
    val color: Int
    val name: String
}