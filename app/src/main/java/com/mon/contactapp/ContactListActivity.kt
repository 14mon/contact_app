package com.mon.contactapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactListActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactViewModel
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ContactAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        viewModel.allContacts.observe(this) { contacts ->
            contacts?.let { adapter.setContacts(it) }
        }
    }
}