package com.k.app

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.k.app.data.LoginUIEvent
import com.k.app.data.LoginUIState
import com.k.app.data.rules.Validator
import com.k.app.navigations.Screen
import com.k.app.navigations.UConnectAppRouter
import com.k.app.screens.User

class LoginViewModel: ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var loginUIState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false  )


    fun onEvent(event:LoginUIEvent){
        when(event){
            is LoginUIEvent.EmailChanged ->{
                loginUIState.value = loginUIState.value.copy(
                        email = event.email
                )

            }
            is LoginUIEvent.PasswordChanged ->{
                loginUIState.value = loginUIState.value.copy(
                        password = event.password
                )

            }
            is LoginUIEvent.LoginButtonClicked ->{
                login()

            }
        }
        validateDataWithRules()
    }


    private fun validateDataWithRules() {

        val emailResult= Validator.validateEmail(
                email = loginUIState.value.email
        )
        val passwordResult= Validator.validatePassword(
                password = loginUIState.value.password
        )
        loginUIState.value = loginUIState.value.copy(
                emailError = emailResult.status,
                passwordError = passwordResult.status
        )
        allValidationsPassed.value = emailResult.status && passwordResult.status

    }

    private fun login() {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {

                Log.d(TAG,"Inside_login_success")
                Log.d(TAG,"${it.isSuccessful}")
                if(it.isSuccessful){
                    loginInProgress.value= false
                    UConnectAppRouter.navigateTo(Screen.HomeScreen)
                }

            }
            .addOnFailureListener {
                Log.d(TAG,"Inside_login_failure")
                Log.d(TAG,"${it.localizedMessage}")
                loginInProgress.value=false



            }
    }
    private fun createUserInFirebase(email:String,password:String){
        loginInProgress.value = true
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                Log.d(TAG,"Inside_OnCompleteListener")
                Log.d(TAG,"Exception= ${it.isSuccessful}")

                loginInProgress.value=false

                if(it.isSuccessful){
                    UConnectAppRouter.navigateTo(Screen.HomeScreen)
                }
            }
            .addOnFailureListener {
                Log.d(TAG,"Inside_OnFailureListener")
                Log.d(TAG,"Exception= ${it.message}")
                Log.d(TAG,"Exception= ${it.localizedMessage}")


            }

    }
    fun getUserDetails(): User {
        // Replace the following example values with your actual logic to get user details
        return User(
                email = "person1@email.com",
                name = "Alley Marie",
                department = "BCA",
                field = "Software Development"
        )
    }

    fun getMultipleUserDetails(): List<User> {
        // Replace the following example values with your actual logic to get multiple user details
        val user1 = User(
                email = "user1@email.com",
                name = "John Doe",
                department = "IT",
                field = "Web Development"
        )

        val user2 = User(
                email = "user2@email.com",
                name = "Jane Doe",
                department = "CS",
                field = "Mobile App Development"
        )
        val user3 = User(
                email = "user1@email.com",
                name = "John Doe",
                department = "IT",
                field = "Web Development"
        )

        val user4 = User(
                email = "user2@email.com",
                name = "Jane Doe",
                department = "CS",
                field = "Mobile App Development"
        )

        return listOf(user1, user2,user3,user4)
    }

    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Inside sign out success")
                UConnectAppRouter.navigateTo(Screen.LoginScreen)
            } else {
                Log.d(TAG, "Inside sign out is not complete")


            }
        }
        firebaseAuth.addAuthStateListener { authStateListener }
    }



}