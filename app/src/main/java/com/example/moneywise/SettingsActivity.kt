package com.example.moneywise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class SettingsActivity : ComponentActivity() {

    private lateinit var modifyButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        modifyButton = findViewById(R.id.btnModifyPersonalInfo)

        modifyButton.setOnClickListener {
            val intent = Intent(this, UpdateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}