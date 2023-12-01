package com.k.app.navigations

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen{
    object SignUpScreen : Screen()
    object TermsAndConditionScreen : Screen()
    object LoginScreen : Screen()
    object HomeScreen : Screen()
}

object UConnectAppRouter {
    var currentScreen : MutableState<Screen> = mutableStateOf(Screen.SignUpScreen)

    fun navigateTo(destination : Screen)
    {
        currentScreen.value = destination
    }
}