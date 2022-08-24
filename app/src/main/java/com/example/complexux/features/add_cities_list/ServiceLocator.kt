package com.example.complexux.features.add_cities_list

import com.example.complexux.features.add_cities_list.data.Repository
import com.example.complexux.features.add_cities_list.domain.use_cases.AddCitiesListInteractor
import com.example.complexux.features.add_cities_list.domain.use_cases.impl.AddCitiesListInteractorImpl

object ServiceLocator {
    fun provideInteractor() : AddCitiesListInteractor{
        val repo = Repository()
        return AddCitiesListInteractorImpl(repo, repo)
    }
}