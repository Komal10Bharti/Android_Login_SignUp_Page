package com.k.app.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.k.app.navigations.Screen
import com.k.app.navigations.UConnectAppRouter
import com.k.app.screens.HomeScreen
import com.k.app.screens.LoginScreen
import com.k.app.screens.SignUpScreen
import com.k.app.screens.TermsAndConditionsScreen

@Composable
fun UConnectApp(){
    Surface (
        modifier = Modifier.fillMaxSize(),
        color= Color.White
    )
    {
        Crossfade(targetState = UConnectAppRouter.currentScreen) { currentState->
            when(currentState.value){
                is Screen.SignUpScreen ->{
                    SignUpScreen()
                }
                is Screen.TermsAndConditionScreen ->{
                    TermsAndConditionsScreen()
                }
                is Screen.LoginScreen ->{
                    LoginScreen()
                }
                is  Screen.HomeScreen ->{
                    HomeScreen()
                }
            }

        }

    }
}