package com.example.moneywise

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.moneywise.R.layout.landing_layout

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(landing_layout)
    }
}