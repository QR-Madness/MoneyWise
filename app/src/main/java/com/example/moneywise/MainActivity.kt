package com.example.moneywise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.moneywise.R.layout.landing_layout

class MainActivity : ComponentActivity() {
    private lateinit var btnInd: Button
    private lateinit var btnAdmin: Button
    private lateinit var btnAbout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(landing_layout)

        btnInd = findViewById(R.id.button)
        btnAdmin = findViewById(R.id.button2)
        btnAbout = findViewById(R.id.btn_aboutUs)

        btnInd.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btnAdmin.setOnClickListener {
            val intent = Intent(this@MainActivity, AdminLoginActivity::class.java)
            startActivity(intent)
        }

        btnAbout.setOnClickListener {
            val intent = Intent(this@MainActivity, AboutUsActivity::class.java)
            startActivity(intent)
        }
    }
}