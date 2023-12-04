package com.example.moneywise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity

class HomePageActivity : ComponentActivity() {

    private lateinit var reportingButton: Button
    private lateinit var budgetingButton: Button
    private lateinit var receiptButton: Button
    private lateinit var settingsButton: Button

    private val TAG = "Home Page"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        reportingButton = findViewById(R.id.btn_reporting)
        budgetingButton = findViewById(R.id.btn_budgeting)
        receiptButton = findViewById(R.id.btn_receipt)
        settingsButton = findViewById(R.id.btn_settings)

        reportingButton.setOnClickListener {
            Log.w(TAG, "Reporting Button clicked")
            val intent = Intent(this, ReportingActivity::class.java)
            startActivity(intent)
        }

        budgetingButton.setOnClickListener {
            Log.w(TAG, "Budgeting Button clicked")
            val intent = Intent(this, ExpensesActivity::class.java)
            startActivity(intent)
        }

        receiptButton.setOnClickListener {
            Log.w(TAG, "Receipt Button clicked")
            val intent = Intent(this, ReceiptActivity::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            Log.w(TAG, "Settings Button clicked")
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}