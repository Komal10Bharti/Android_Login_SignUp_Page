package com.k.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.k.app.R
import com.k.app.components.HeadingComponent
import com.k.app.components.NormalTextComponent
import com.k.app.navigations.Screen
import com.k.app.navigations.SystemBackButtonHandler
import com.k.app.navigations.UConnectAppRouter

@Composable
fun TermsAndConditionsScreen(){
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(28.dp)
    ){
        //HeadingComponent(value = stringResource(id = R.string.terms_conditions_header))
        NormalTextComponent(value = "By using this app, you agree to abide by these terms. You are responsible for maintaining the confidentiality of your account information. Prohibited activities include unauthorized access and any actions that violate these terms. This platform reserves the right to terminate accounts for conduct that breaches these terms. The app is provided as is, and we make no warranties regarding its accuracy or completeness. We may update these terms, and continued use constitutes acceptance of any changes." )
    }
    SystemBackButtonHandler {
        UConnectAppRouter.navigateTo(Screen.SignUpScreen )
    }
}
@Preview
@Composable
fun TermsAndConditionsScreenPreview()
{
    TermsAndConditionsScreen()
}
