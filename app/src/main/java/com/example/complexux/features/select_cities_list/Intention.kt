package com.example.complexux.features.select_cities_list

sealed interface Intention{
    data class SelectList(val index: Int) : Intention
    data class StartDragAndDrop(val index: Int) : Intention
}