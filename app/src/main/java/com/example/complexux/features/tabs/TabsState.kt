package com.example.complexux.features.tabs

sealed interface TabsState {
    object FirstTab : TabsState
    object SecondTab : TabsState
}