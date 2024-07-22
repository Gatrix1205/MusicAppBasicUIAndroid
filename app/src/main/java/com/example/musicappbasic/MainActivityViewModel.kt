package com.example.musicappbasic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.DrawerScreen.Account)

    val currentScreen: MutableState<Screen>
        get() = _currentScreen

    fun setCurrentScreen(screen:Screen){
        _currentScreen.value = screen
    }
}