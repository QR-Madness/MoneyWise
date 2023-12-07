package com.example.moneywise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UpdateAccountActivity : ComponentActivity() {

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnUpdateInfo: Button
    private lateinit var btnDeleteAccount: Button

    private val db = Firebase.firestore
    private val currentUser = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_account)

        editFirstName = findViewById(R.id.editFirstName)
        editLastName = findViewById(R.id.editLastName)
        editPassword = findViewById(R.id.editPassword)
        btnUpdateInfo = findViewById(R.id.btnUpdateInfo)
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount)

        btnUpdateInfo.setOnClickListener {
            val newFirstName = editFirstName.text.toString()
            val newLastName = editLastName.text.toString()
            val newPassword = editPassword.text.toString()

            currentUser?.let { it1 -> updateUserInfo(it1.uid, newFirstName, newLastName, newPassword) }
        }

        btnDeleteAccount.setOnClickListener {
            currentUser?.let { it1 -> deleteAccountAndUserData(it1.uid) }
        }
    }

    fun updateUserInfo(userId: String, newFirstName: String, newLastName: String, newPassword: String) {
        val auth = Firebase.auth
        val db = Firebase.firestore
        val userDocRef = db.collection("user-data").document(userId)

        // Create a map with the fields to update
        val updates = mapOf(
            "firstName" to newFirstName,
            "lastName" to newLastName
        )

        // Update the document with the new information
        userDocRef.update(updates)
            .addOnSuccessListener {
                // User information update successful
                Log.d("UpdateUserInfo", "User information update successful")

                // update the password
                updatePassword(auth.currentUser, newPassword)
            }
            .addOnFailureListener { e ->
                // User information update failed
                Log.e("UpdateUserInfo", "User information update failed", e)
            }
    }

    private fun updatePassword(currentUser: FirebaseUser?, newPassword: String) {
        currentUser?.let { user ->
            val credential = EmailAuthProvider.getCredential(user.email!!, newPassword)
            user.reauthenticate(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        // Password reauthentication successful, update the password
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { passwordTask ->
                                if (passwordTask.isSuccessful) {
                                    // Password update successful
                                    Log.d("UpdateUserInfo", "Password update successful")
                                } else {
                                    // Password update failed
                                    Log.e("UpdateUserInfo", "Password update failed")
                                }
                            }
                    } else {
                        // Password reauthentication failed
                        Log.e("UpdateUserInfo", "Password reauthentication failed")
                    }
                }
        }
    }

    fun deleteAccountAndUserData(userId: String) {
        val auth = Firebase.auth

        // Delete user account
        auth.currentUser?.delete()
            ?.addOnSuccessListener {
                // Delete user-related data in other collections
                deleteUserData(userId)
                deleteExpenses(userId)
                deleteIncome(userId)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            ?.addOnFailureListener { exception ->
            }
    }

    fun deleteUserData(userId: String) {
        val db = Firebase.firestore

        // Delete the user's data in the user-data collection
        db.collection("user-data").document(userId)
            .delete()
            .addOnSuccessListener {
            }
            .addOnFailureListener { exception ->
            }
    }

    fun deleteExpenses(userId: String) {
        val db = Firebase.firestore

        // Delete the user's expenses in the expenses collection
        db.collection("expenses")
            .whereEqualTo("username", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                }
                // Expenses deletion successful
            }
            .addOnFailureListener { exception ->
                // Expenses deletion failed
            }
    }

    fun deleteIncome(userId: String) {
        val db = Firebase.firestore

        // Delete the user's income in the "income" collection
        db.collection("income")
            .whereEqualTo("username", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                }
                // Income deletion successful
            }
            .addOnFailureListener { exception ->
                // Income deletion failed
            }
    }
}