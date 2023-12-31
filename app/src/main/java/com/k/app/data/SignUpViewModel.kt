package com.k.app.data

//import android.service.autofill.Validator
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.k.app.data.rules.Validator
import com.k.app.navigations.Screen
import com.k.app.navigations.UConnectAppRouter

//import javax.xml.validation.Validator

class SignUpViewModel: ViewModel() {
    private val TAG = SignUpViewModel::class.simpleName
    var allValidationsPassed = mutableStateOf(false)
    var registrationUIState   = mutableStateOf(RegistrationUIState())
     var signupInProgress = mutableStateOf(false)


    fun onEvent(event: SignupUIEvent) {
        validateDataWithRules()
        when (event) {
            is SignupUIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                        firstName = event.firstName
                )
                printState()


            }

            is SignupUIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                        lastName = event.lastName
                )
                printState()



            }

            is SignupUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                        email = event.email
                )
                printState()


            }

            is SignupUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                        password = event.password
                )
                printState()



            }
            is SignupUIEvent.RegisterButtonClicked ->{
                signUp()
            }
            is SignupUIEvent.PrivacyPolicyCheckBoxClicked ->{
                registrationUIState.value=registrationUIState.value.copy(
                        privacyPolicyAccepted = event.status
                )

            }
        }
        validateDataWithRules()
    }

    private fun signUp() {
        Log.d(TAG,"Inside_signUp")
        printState()
//        validateDataWithRules()
        createUserInFirebase(
                email = registrationUIState.value.email,
                password =registrationUIState.value.password
        )

    }

    private fun validateDataWithRules() {
        val fNameResult = Validator.validateFirstName(
                fName = registrationUIState.value.firstName
        )
        val lNameResult= Validator.validateLastName(
                lName = registrationUIState.value.lastName
        )
        val emailResult= Validator.validateEmail(
                email = registrationUIState.value.email
        )
        val passwordResult= Validator.validatePassword(
                password = registrationUIState.value.password
        )
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
             statusValue = registrationUIState.value.privacyPolicyAccepted
        )
        Log.d(TAG,"Inside_validateDataWithRules")
        Log.d(TAG,"fNameResult= $fNameResult ")
        Log.d(TAG,"fNameResult= $lNameResult ")
        Log.d(TAG,"fNameResult= $emailResult")
        Log.d(TAG,"fNameResult= $passwordResult")
        Log.d(TAG,"privacyPolicyResult= $privacyPolicyResult")
        registrationUIState.value=registrationUIState.value.copy(
                firstNameError = fNameResult.status,
                lastNameError = lNameResult.status,
                emailError = emailResult.status,
                passwordError = passwordResult.status,
                privacyPolicyError = privacyPolicyResult.status
        )
        allValidationsPassed.value = fNameResult.status &&
                lNameResult.status &&
                emailResult.status &&
                passwordResult.status &&
                privacyPolicyResult.status
    }

    private  fun printState(){
        Log.d(TAG,"Inside_printState")
        Log.d(TAG,registrationUIState.value.toString())

    }
    private fun createUserInFirebase(email:String,password:String){
        signupInProgress.value = true
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                Log.d(TAG,"Inside_OnCompleteListener")
                Log.d(TAG,"Exception= ${it.isSuccessful}")

                signupInProgress.value=false

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
    fun logout(){
       val firebaseAuth = FirebaseAuth.getInstance()
//       firebaseAuth.signOut()
        val authStateListener = AuthStateListener { auth->
            if(auth.currentUser==null){
                Log.d(TAG,"Inside sign out success")
                UConnectAppRouter.navigateTo(Screen.LoginScreen)
           }else{
                Log.d(TAG,"Inside sign out is not complete")


            }
       }
        firebaseAuth.addAuthStateListener(authStateListener)
        firebaseAuth.signOut()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }


}
