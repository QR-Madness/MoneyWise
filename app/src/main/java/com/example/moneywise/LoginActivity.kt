package com.example.moneywise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : ComponentActivity() {
    // Firebase access
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var signInButton: Button
    lateinit var signUpButton: Button
    private val TAG = "Auth: Login"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_indiviual)
        signInButton = findViewById(R.id.btn_signInInd)
        signUpButton = findViewById(R.id.btn_signUp)
        signInButton.setOnClickListener {
            val indUserId = findViewById<EditText>(R.id.edit_usernameInd).text.toString()
            val password = findViewById<EditText>(R.id.edit_passwordInd).text.toString()

            // Init firebase
        firebaseAuth = Firebase.auth

        // Set listener(s)
    }

        signUpButton.setOnClickListener {
            Log.i("TAG", "SignUp Button  is  clicked!")
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Navigating to SignUp View", Toast.LENGTH_SHORT).show()
        }
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