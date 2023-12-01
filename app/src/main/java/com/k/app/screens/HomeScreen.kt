package com.k.app.screens

//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.k.app.LoginViewModel
import com.k.app.R
import com.k.app.components.ButtonComponent
import com.k.app.components.HeadingComponent

data class User(
    val email: String,
    val name: String,
    val department: String,
    val field: String
)
@Composable
fun HomeScreen(loginViewModel: LoginViewModel = viewModel()) {
    //val scaffoldState = rememberScaffoldState()
    //val coroutineScope = rememberCoroutineScope()


    Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
    ) {
        LazyColumn(
                modifier = Modifier.fillMaxSize()
        ) {
            item {
                HeadingComponent(value = stringResource(R.string.home))
            }

            items(count = loginViewModel.getMultipleUserDetails().size) {
                index ->
                UserDetailsItem(loginViewModel.getMultipleUserDetails()[index])
                Spacer(modifier = Modifier.height(16.dp)) // Add padding between user details
            }

            item {
                ButtonComponent(
                        value = stringResource(R.string.logout),
                        onButtonClicked = {
                            loginViewModel.logout()
                        },
                        isEnabled = true
                )
            }
        }
    }
}
    @Composable
    fun UserDetailsGrid(users : List<User>) {
        LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
        ) {
            items(users.size) { index ->
                UserDetailsItem(users[index])
                Spacer(modifier = Modifier.height(16.dp)) // Add padding between user details
            }
        }
    }

    @Composable
    fun UserDetailsItem(user: User) {
        Surface(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clip(MaterialTheme.shapes.small)
        ) {
            Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(16.dp)
            ) {
                UserDetailsText("Email: ${user.email}")
                UserDetailsText("Name: ${user.name}")
                UserDetailsText("Department: ${user.department}")
                UserDetailsText("Field: ${user.field}")
            }
        }
    }

@Composable
fun UserDetailsText(detail: String) {
    androidx.compose.material3.Text(
            text = detail,
            style = MaterialTheme.typography.headlineMedium,
            color = contentColorFor(MaterialTheme.colorScheme.primary)
    )
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}