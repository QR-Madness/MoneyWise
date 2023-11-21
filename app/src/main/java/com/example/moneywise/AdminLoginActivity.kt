package com.example.moneywise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class AdminLoginActivity : ComponentActivity() {
    lateinit var signInButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        signInButton = findViewById(R.id.btn_signInAdmin)
        //authentication validation
        signInButton.setOnClickListener {
            val adminUserId = findViewById<EditText>(R.id.edit_usernameAdmin).text.toString()
            val password = findViewById<EditText>(R.id.edit_passwordAdmin).text.toString()


    }
}}