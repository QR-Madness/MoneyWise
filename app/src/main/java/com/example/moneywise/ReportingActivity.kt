package com.example.moneywise

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReportingActivity : ComponentActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var reportContainer: LinearLayout
    private lateinit var expensesText: TextView
    private lateinit var incomeText: TextView
    private val TAG = "ReportingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reporting)

        db = Firebase.firestore

        reportContainer = findViewById(R.id.reportContainer)
        expensesText = findViewById(R.id.expensesText)
        incomeText = findViewById(R.id.incomeText)

        generateReport()
    }

    private fun generateReport() {
        val userId = Firebase.auth.currentUser?.uid

        if (userId != null) {
            fetchAndProcessExpenses(userId)
            fetchAndProcessIncome(userId)
        } else {
            Log.w(TAG, "User not authenticated")
        }
    }

    private fun fetchAndProcessExpenses(userId: String) {
        db.collection("expenses")
            .whereEqualTo("username", userId)
            .get()
            .addOnSuccessListener { documents ->
                // Process and display the total expenses per category
                processData(documents, expensesText)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting expenses", exception)
            }
    }

    private fun fetchAndProcessIncome(userId: String) {
        db.collection("income")
            .whereEqualTo("username", userId)
            .get()
            .addOnSuccessListener { documents ->
                // Process and display the total income
                processIncome(documents, incomeText)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting income", exception)
            }
    }

    private fun processData(documents: Iterable<QueryDocumentSnapshot>, textView: TextView) {
        val expensesMap = mutableMapOf<String, Long>()

        // Calculate total expenses per category
        for (document in documents) {
            val amount = document.getLong("amount") ?: 0
            val category = document.getString("category") ?: "Uncategorized"

            expensesMap[category] = expensesMap.getOrDefault(category, 0) + amount
        }

        // Display the data in the UI
        val builder = StringBuilder()
        for ((category, total) in expensesMap) {
            Log.d(TAG, "Category: $category, Total: $total")

            // Append data to a string for display
            builder.append("Category: $category, Total: $total\n")
        }

        // Set the text in the TextView
        textView.text = builder.toString()
    }

    private fun processIncome(documents: Iterable<QueryDocumentSnapshot>, textView: TextView) {
        var totalIncome = 0L

        // Calculate total income
        for (document in documents) {
            val amount = document.getLong("amount") ?: 0
            totalIncome += amount
        }

        // Display the total income in the UI
        textView.text = "Total Income: $totalIncome"
    }
}