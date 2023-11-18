package com.example.moneywise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    // Firebase access
    private lateinit var firebaseAuth: FirebaseAuth

    private val TAG = "Auth: Login"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init firebase
        firebaseAuth = Firebase.auth

        // Set listener(s)
    }

    private fun loginUser() {
        // TODO parse & validate inputs into a model
        // TODO complete this register controller
        val userLoginResult = firebaseAuth.signInWithEmailAndPassword("someemail", "somepassword")
        userLoginResult.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.w(TAG, "Successful")
            } else {
                Log.w(TAG, "Unsuccessful")
            }
        }
    }
}