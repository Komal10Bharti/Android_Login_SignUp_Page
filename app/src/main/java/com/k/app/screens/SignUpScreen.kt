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

import com.k.app.R
import com.k.app.components.ButtonComponent
import com.k.app.components.CheckboxComponent
import com.k.app.components.ClickableLoginTextComponent
import com.k.app.components.DividerTextComponent
import com.k.app.components.HeadingComponent
import com.k.app.components.MyTextFieldComponent
import com.k.app.components.NormalTextComponent
import com.k.app.components.PasswordFieldComponent
import com.k.app.data.SignUpViewModel
import com.k.app.data.SignupUIEvent
import com.k.app.navigations.Screen
import com.k.app.navigations.UConnectAppRouter


@Composable
fun SignUpScreen(SignUpViewModel: SignUpViewModel = viewModel()) {
    Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
    ) {
        Surface(
                //color = Color(0xFFADD8E6),
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(28.dp)
        )  {
            Column(modifier = Modifier.fillMaxSize()) {
                HeadingComponent(value = "UConnect")
                NormalTextComponent(value = stringResource(id = R.string.hello))
                HeadingComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                        labelValue = stringResource(id = R.string.first_name),
                        painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            SignUpViewModel.onEvent(SignupUIEvent.FirstNameChanged(it))
                        },
                        errorStatus = SignUpViewModel.registrationUIState.value.firstNameError
                )

                MyTextFieldComponent(
                        labelValue = stringResource(id = R.string.last_name),
                        painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            SignUpViewModel.onEvent(SignupUIEvent.LastNameChanged(it))
                        },
                        errorStatus = SignUpViewModel.registrationUIState.value.lastNameError
                )

                MyTextFieldComponent(
                        labelValue = stringResource(id = R.string.email),
                        painterResource = painterResource(id = R.drawable.ii),
                        onTextSelected = {
                            SignUpViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                        },
                        errorStatus = SignUpViewModel.registrationUIState.value.emailError
                )

                PasswordFieldComponent(
                        labelValue = stringResource(id = R.string.password),
                        painterResource = painterResource(id = R.drawable.lock),
                        onTextSelected = {
                            SignUpViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                        },
                        errorStatus = SignUpViewModel.registrationUIState.value.passwordError
                )

                CheckboxComponent(value = stringResource(id = R.string.terms_conditions),
                        onTextSelected = {
                            UConnectAppRouter.navigateTo(Screen.TermsAndConditionScreen)
                        },
                        onCheckedChange = {
                            SignUpViewModel.onEvent(SignupUIEvent.PrivacyPolicyCheckBoxClicked(it))
                        }
                )

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                        value = stringResource(id = R.string.register),
                        onButtonClicked = {
                            SignUpViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)
                        },
                        isEnabled = SignUpViewModel.allValidationsPassed.value
                )




                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                    UConnectAppRouter.navigateTo(Screen.LoginScreen)
                })
            }


        }
        if (SignUpViewModel.signupInProgress.value) {
            CircularProgressIndicator()
        }


    }
}

@Preview
    @Composable
    fun DefaultPreviewOfSignUpScreen() {
        SignUpScreen()
    }

