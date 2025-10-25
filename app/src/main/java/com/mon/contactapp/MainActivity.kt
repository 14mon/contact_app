package com.mon.contactapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactViewModel
    private var selectedAvatarResourceId = AvatarManager.DEFAULT_AVATAR

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
            val btnSelectAvatar = findViewById<Button>(R.id.btnSelectAvatar)
            val selectedAvatarCard = findViewById<MaterialCardView>(R.id.selectedAvatarCard)
            val selectedAvatarImageView = findViewById<ImageView>(R.id.selectedAvatarImageView)

            Log.d("MainActivity", "All views found")

            // Avatar selection functionality
            val avatarClickListener = {
                showAvatarSelectionDialog { avatarResourceId ->
                    selectedAvatarResourceId = avatarResourceId
                    selectedAvatarImageView.setImageResource(avatarResourceId)
                }
            }

            btnSelectAvatar.setOnClickListener { avatarClickListener() }
            selectedAvatarCard.setOnClickListener { avatarClickListener() }

            btnSave.setOnClickListener {
                val name = etName.text.toString().trim()
                val dob = etDoB.text.toString().trim()
                val email = etEmail.text.toString().trim()

                if (name.isNotEmpty() && dob.isNotEmpty() && email.isNotEmpty()) {
                    val contact = Contact(
                        name = name,
                        dateOfBirth = dob,
                        email = email,
                        avatarResourceId = selectedAvatarResourceId
                    )
                    viewModel.insert(contact)

                    Toast.makeText(this, "Contact Saved!", Toast.LENGTH_SHORT).show()

                    etName.text.clear()
                    etDoB.text.clear()
                    etEmail.text.clear()
                    selectedAvatarResourceId = AvatarManager.DEFAULT_AVATAR
                    selectedAvatarImageView.setImageResource(selectedAvatarResourceId)
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

    private fun showAvatarSelectionDialog(onAvatarSelected: (Int) -> Unit) {
        val dialog = AvatarSelectionDialog(this, selectedAvatarResourceId, onAvatarSelected)
        dialog.show()
    }
}