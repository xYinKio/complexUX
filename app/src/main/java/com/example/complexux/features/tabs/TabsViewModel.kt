package com.example.complexux.features.tabs

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TabsViewModel : ViewModel() {

    private val _state = MutableStateFlow<TabsState>(TabsState.FirstTab)
    val state = _state.asStateFlow()

    fun selectFirstTab(){
        _state.value = TabsState.FirstTab
    }

    fun selectSecondTab(){
        _state.value = TabsState.SecondTab
    }

}