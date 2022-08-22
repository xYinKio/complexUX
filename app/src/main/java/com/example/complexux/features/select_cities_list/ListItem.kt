package com.example.complexux.features.select_cities_list

sealed interface ListItem {
    data class Data(val citiesList: CitiesList) : ListItem
    object Add : ListItem
}