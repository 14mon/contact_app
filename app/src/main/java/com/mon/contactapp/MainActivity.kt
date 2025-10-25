package com.mon.contactapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_main)
            Log.d("MainActivity", "Layout set successfully")

            viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
            Log.d("MainActivity", "ViewModel initialized")

            val etName = findViewById<EditText>(R.id.etName)
            val etDoB = findViewById<EditText>(R.id.etDoB)
            val etEmail = findViewById<EditText>(R.id.etEmail)
            val btnSave = findViewById<Button>(R.id.btnSaveDetails)
            val btnView = findViewById<Button>(R.id.btnViewDetails)

            Log.d("MainActivity", "All views found")

            btnSave.setOnClickListener {
                val name = etName.text.toString().trim()
                val dob = etDoB.text.toString().trim()
                val email = etEmail.text.toString().trim()

                if (name.isNotEmpty() && dob.isNotEmpty() && email.isNotEmpty()) {
                    val contact = Contact(name = name, dateOfBirth = dob, email = email)
                    viewModel.insert(contact)

                    Toast.makeText(this, "Contact Saved!", Toast.LENGTH_SHORT).show()

                    etName.text.clear()
                    etDoB.text.clear()
                    etEmail.text.clear()
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }

            btnView.setOnClickListener {
                val intent = Intent(this, ContactListActivity::class.java)
                startActivity(intent)
            }

            Toast.makeText(this, "App Started!", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}