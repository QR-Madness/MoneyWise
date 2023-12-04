package com.example.moneywise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExpensesActivity : ComponentActivity() {

    // Firestore access
    private lateinit var db: FirebaseFirestore

    private lateinit var amountText: EditText
    private lateinit var categoryText: EditText
    private lateinit var enterButton: Button
    private lateinit var incomeButton: Button
    private val TAG = "Expenses"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)

        db = Firebase.firestore

        amountText = findViewById(R.id.edit_expense)
        categoryText = findViewById(R.id.edit_category)
        enterButton = findViewById(R.id.btn_expenses)
        incomeButton = findViewById(R.id.btn_income)



        enterButton.setOnClickListener {
            val amount = Integer.parseInt(amountText.text.toString())
            val category = categoryText.text.toString()

            Log.w(TAG, "Enter button clicked")
            inputExpenses(amount, category)
        }

        incomeButton.setOnClickListener {
            Log.w(TAG, "Income button clicked")
            val intent = Intent(this, IncomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun inputExpenses(amount: Int, category: String) {
        Log.d(TAG, "Amount: $amount, Category: $category")

        val currentUser = Firebase.auth.currentUser?.uid

        // Check if the user is authenticated
        if (currentUser != null) {

            val expense = hashMapOf(
                "username" to currentUser,
                "amount" to amount,
                "category" to category
            )


            val createResult = db.collection("expenses").add(expense)
            createResult.addOnCompleteListener {
                Log.w(TAG, "Successful submission")
                finish()
            }
            createResult.addOnFailureListener {
                Log.w(TAG, "Failed to insert expense firestore records")
            }
        } else {
            Log.w(TAG, "User not authenticated")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}