package com.mon.contactapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AvatarSelectionDialog(
    context: Context,
    private val currentAvatarResourceId: Int = AvatarManager.DEFAULT_AVATAR,
    private val onAvatarSelected: (Int) -> Unit
) : Dialog(context) {

    private var selectedAvatarResourceId = currentAvatarResourceId
    private lateinit var avatarAdapter: AvatarSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_avatar_selection)

        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.avatarRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        avatarAdapter = AvatarSelectionAdapter(currentAvatarResourceId) { avatarResourceId ->
            selectedAvatarResourceId = avatarResourceId
        }
        recyclerView.adapter = avatarAdapter
    }

    private fun setupButtons() {
        findViewById<MaterialButton>(R.id.btnCancel).setOnClickListener {
            dismiss()
        }

        findViewById<MaterialButton>(R.id.btnSelect).setOnClickListener {
            onAvatarSelected(selectedAvatarResourceId)
            dismiss()
        }
    }

    private inner class AvatarSelectionAdapter(
        private var selectedAvatarId: Int,
        private val onAvatarClicked: (Int) -> Unit
    ) : RecyclerView.Adapter<AvatarSelectionAdapter.AvatarViewHolder>() {

        inner class AvatarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
            val selectionIndicator: ImageView = itemView.findViewById(R.id.selectionIndicator)
            val cardView: MaterialCardView = itemView as MaterialCardView

            fun bind(avatarResourceId: Int, position: Int) {
                avatarImageView.setImageResource(avatarResourceId)

                val isSelected = avatarResourceId == selectedAvatarId
                selectionIndicator.visibility = if (isSelected) View.VISIBLE else View.GONE

                // Update card appearance based on selection
                cardView.strokeWidth = if (isSelected) 4 else 2
                cardView.strokeColor = if (isSelected) {
                    context.getColor(R.color.md_theme_primary)
                } else {
                    context.getColor(R.color.md_theme_outline_variant)
                }

                cardView.setOnClickListener {
                    val oldSelectedPosition = getSelectedPosition()
                    selectedAvatarId = avatarResourceId
                    onAvatarClicked(avatarResourceId)

                    // Update the previously selected item
                    if (oldSelectedPosition != -1) {
                        notifyItemChanged(oldSelectedPosition)
                    }
                    // Update the newly selected item
                    notifyItemChanged(position)
                }
            }

            private fun getSelectedPosition(): Int {
                return AvatarManager.avatarList.indexOf(selectedAvatarId)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_avatar_selection, parent, false)
            return AvatarViewHolder(view)
        }

        override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
            val avatarResourceId = AvatarManager.avatarList[position]
            holder.bind(avatarResourceId, position)
        }

        override fun getItemCount() = AvatarManager.avatarList.size
    }
}