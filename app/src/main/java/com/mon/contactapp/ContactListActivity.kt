package com.mon.contactapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ContactListActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactViewModel
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val fabAddContact = findViewById<ExtendedFloatingActionButton>(R.id.fabAddContact)
        val emptyStateLayout = findViewById<LinearLayout>(R.id.emptyStateLayout)
        val listHeader = findViewById<LinearLayout>(R.id.listHeader)
        val contactCountChip = findViewById<Chip>(R.id.contactCountChip)

        adapter = ContactAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        viewModel.allContacts.observe(this) { contacts ->
            contacts?.let {
                adapter.setContacts(it)

                // Update UI based on contact count
                if (it.isEmpty()) {
                    emptyStateLayout.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    listHeader.visibility = View.GONE
                } else {
                    emptyStateLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    listHeader.visibility = View.VISIBLE
                    contactCountChip.text = it.size.toString()
                }
            }
        }

        // Handle FAB click to add new contact
        fabAddContact.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Handle toolbar navigation (back button)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}