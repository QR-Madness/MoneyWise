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

class IncomeActivity : ComponentActivity() {

    // Firestore access
    private lateinit var db: FirebaseFirestore

    private lateinit var amountText: EditText
    private lateinit var enterButton: Button
    private val TAG = "Income"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)

        db = Firebase.firestore

        amountText = findViewById(R.id.edit_expense)
        enterButton = findViewById(R.id.btn_income)

        enterButton.setOnClickListener {
            val amount = Integer.parseInt(amountText.text.toString())

            Log.w(TAG, "Enter button clicked")
            inputIncome(amount)
        }
    }

    private fun inputIncome(amount: Int) {
        Log.d(TAG, "Amount: $amount")

        val currentUser = Firebase.auth.currentUser

        // Check if the user is authenticated
        if (currentUser != null) {
            val userId = currentUser.uid

            val income = hashMapOf(
                "username" to userId,
                "amount" to amount
            )


            val createResult = db.collection("income").add(income)
            createResult.addOnCompleteListener {
                Log.w(TAG, "Successful submission")
                finish()
            }
            createResult.addOnFailureListener {
                Log.w(TAG, "Failed to insert income firestore records")
            }
        } else {
            // Handle the case where the user is not authenticated
            Log.w(TAG, "User not authenticated")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}