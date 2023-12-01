package com.k.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.k.app.LoginViewModel
import com.k.app.R
import com.k.app.components.ButtonComponent
import com.k.app.components.ClickableLoginTextComponent
import com.k.app.components.DividerTextComponent
import com.k.app.components.HeadingComponent
import com.k.app.components.MyTextFieldComponent
import com.k.app.components.NormalTextComponent
import com.k.app.components.PasswordFieldComponent
import com.k.app.components.UnderinedTextComponent
import com.k.app.data.LoginUIEvent
import com.k.app.navigations.Screen
import com.k.app.navigations.SystemBackButtonHandler
import com.k.app.navigations.UConnectAppRouter


@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
    ) {
        Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(28.dp)
        ) {
            Column(
                    modifier = Modifier
                        .fillMaxSize()
            ) {
                NormalTextComponent(value = stringResource(id = R.string.login))
                HeadingComponent(value = stringResource(id = R.string.welcome))
                Spacer(modifier = Modifier.height(20.dp))


                MyTextFieldComponent(
                        labelValue = stringResource(id = R.string.email),
                        painterResource(id = R.drawable.ii),
                        onTextSelected = {
                            loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))

                        },
                        errorStatus = loginViewModel.loginUIState.value.emailError

                )

                PasswordFieldComponent(
                        labelValue = stringResource(id = R.string.password),
                        painterResource(id = R.drawable.lock),
                        onTextSelected = {
                            loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))


                        },
                        errorStatus = loginViewModel.loginUIState.value.passwordError
                )
                Spacer(modifier = Modifier.height(40.dp))

                UnderinedTextComponent(value = stringResource(id = R.string.show_password))
                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                        value = stringResource(id = R.string.login),
                        onButtonClicked = {
                            loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)

                        },
                        isEnabled = loginViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()
                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                    UConnectAppRouter.navigateTo(Screen.SignUpScreen)
                })
            }
//        if(loginViewModel.loginInProgress.value) {
//            CircularProgressIndicator()
//        }
        }
        if (loginViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }

    }


        SystemBackButtonHandler {
            UConnectAppRouter.navigateTo(Screen.SignUpScreen)
        }
}








@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}