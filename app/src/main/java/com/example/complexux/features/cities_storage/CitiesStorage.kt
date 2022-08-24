package com.example.complexux.features.cities_storage

object CitiesStorage {

    val cities = listOf(
        City("Москва", "1147 г"),
        City("Стамбул", "1923 г"),
        City("Лондон", "47 г"),
        City("Санкт-Петербург", "1703 г"),
        City("Берлин", "1237 г"),
        City("Мадрид", "1983 г"),
        City("Киев", "482 г"),
        City("Рим", "753 г до н. э."),
        City("Париж", "53 г до н. э."),
        City("Минск", "1067 г"),
    )

    val citiesLists = mutableListOf(
        CitiesList(
            name = "Еврода",
            fullName ="Города европейской части крупнейшего континента на Земле",
            cities = cities.subList(0, 4),
            color = 0xFF1212
        )
    )

}