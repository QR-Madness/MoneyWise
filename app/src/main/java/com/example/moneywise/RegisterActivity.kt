package com.example.moneywise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : ComponentActivity() {
    // Firebase access
    private lateinit var firebaseAuth: FirebaseAuth
    // Firestore access
    private lateinit var db: FirebaseFirestore

    private lateinit var submitBtn: Button
    // logging tag used to identify the activity
    private val TAG = "Auth: Registration"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Init firebase
        firebaseAuth = Firebase.auth
        db = Firebase.firestore

        // define UI components
        submitBtn = findViewById(R.id.btn_signUpasInd)
        val textViewIndId = findViewById<TextView>(R.id.edit_indId)
        val textViewIndFirstName = findViewById<TextView>(R.id.edit_indFirstName)
        val textViewIndLastName = findViewById<TextView>(R.id.edit_indLastName)
        val textViewIndpassword = findViewById<TextView>(R.id.edit_setIndpassword)


        // Set listener(s)
        submitBtn.setOnClickListener {
            val username = textViewIndId.text.toString()
            val firstName = textViewIndFirstName.text.toString()
            val lastName = textViewIndLastName.text.toString()
            val password = textViewIndpassword.text.toString()
            registerUser(username, firstName, lastName, password)
        }
    }

    private fun registerUser(username: String, firstName: String, lastName: String, password: String) {
        val user = hashMapOf(
            "username" to username,
            "firstName" to firstName,
            "lastName" to lastName,
            "password" to password,
            "isAdmin" to false
        )
        // TODO complete this register controller
        // Ref: https://firebase.google.com/docs/firestore/quickstart#kotlin+ktx
        val userCreationResult = firebaseAuth.createUserWithEmailAndPassword(username, password)
        userCreationResult.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val createResult = db.collection("user-data").add(user)
                createResult.addOnCompleteListener {
                    Log.w(TAG, "Successful registration")
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                createResult.addOnFailureListener {
                    Log.w(TAG, "Failed to insert registration firestore records")
                }
            } else {
                Log.w(TAG, "Failed registration")
            }
        }
    }
}