package com.example.complexux.features.add_cities_list.ui

sealed interface Intention{
    data class TypeName(val text: String) : Intention
    data class TypeFullName(val text: String) : Intention
    data class SelectColor(val color: Int) : Intention
    data class Filter(val text: String) : Intention
    data class SelectCity(val city: City) : Intention
    data class UnselectCity(val city: City) : Intention
    object Complete : Intention
}