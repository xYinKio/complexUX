package com.example.complexux.features.cities_storage

import android.graphics.Color

object CitiesStorage {

    val cities = listOf(
        CityInStorage("Москва", "1147 г"),
        CityInStorage("Стамбул", "1923 г"),
        CityInStorage("Лондон", "47 г"),
        CityInStorage("Санкт-Петербург", "1703 г"),
        CityInStorage("Берлин", "1237 г"),
        CityInStorage("Мадрид", "1983 г"),
        CityInStorage("Киев", "482 г"),
        CityInStorage("Рим", "753 г до н. э."),
        CityInStorage("Париж", "53 г до н. э."),
        CityInStorage("Минск", "1067 г"),
    )

    val citiesLists = mutableListOf(
        CitiesListInStorage(
            name = "Еврода",
            fullName ="Города европейской части крупнейшего континента на Земле",
            cities = cities.subList(0, 4),
            color = Color.parseColor("#332500")
        )
    )

}