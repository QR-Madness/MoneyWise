package com.example.moneywise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    // Firebase access
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var submitBtn: Button
    // logging tag used to identify the activity
    private val TAG = "Auth: Registration"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Init firebase
        firebaseAuth = Firebase.auth

        // define UI components
        submitBtn = findViewById(R.id.RegisterBtn)

        // Set listener(s)
        submitBtn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        // TODO parse & validate inputs into a model
        // TODO complete this register controller
        val userCreationResult = firebaseAuth.createUserWithEmailAndPassword("someemail", "somepassword")
        userCreationResult.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.w(TAG, "Successful registration")
            } else {
                Log.w(TAG, "Unsuccessful registration")
            }
        }
    }
}