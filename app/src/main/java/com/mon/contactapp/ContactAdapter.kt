package com.mon.contactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var contacts = emptyList<Contact>()

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.contactAvatarImageView)
        val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        val dobTextView: TextView = itemView.findViewById(R.id.tvDoB)
        val emailTextView: TextView = itemView.findViewById(R.id.tvEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]

        // Set avatar image
        val avatarResourceId = AvatarManager.getValidAvatarOrDefault(contact.avatarResourceId)
        holder.avatarImageView.setImageResource(avatarResourceId)

        // Set contact information
        holder.nameTextView.text = contact.name
        holder.dobTextView.text = contact.dateOfBirth
        holder.emailTextView.text = contact.email
    }

    override fun getItemCount() = contacts.size

    fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }
}