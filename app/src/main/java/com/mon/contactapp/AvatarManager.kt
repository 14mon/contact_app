package com.mon.contactapp

import androidx.annotation.DrawableRes

object AvatarManager {

    // Available avatar resource IDs
    @DrawableRes
    val avatarList = listOf(
        R.drawable.avatar_1,
        R.drawable.avatar_2,
        R.drawable.avatar_3

    )

    // Default avatar when none is selected
    @DrawableRes
    val DEFAULT_AVATAR: Int = R.drawable.avatar_1

    /**
     * Get avatar resource ID by index
     * @param index The index of the avatar in the list
     * @return The resource ID of the avatar, or default if index is invalid
     */
    @DrawableRes
    fun getAvatarByIndex(index: Int): Int {
        return if (index in avatarList.indices) {
            avatarList[index]
        } else {
            DEFAULT_AVATAR
        }
    }

    /**
     * Get the index of an avatar resource ID
     * @param resourceId The resource ID to find
     * @return The index of the avatar, or 0 if not found
     */
    fun getAvatarIndex(@DrawableRes resourceId: Int): Int {
        return avatarList.indexOf(resourceId).takeIf { it != -1 } ?: 0
    }

    /**
     * Get a random avatar resource ID
     * @return A random avatar resource ID
     */
    @DrawableRes
    fun getRandomAvatar(): Int {
        return avatarList.random()
    }

    /**
     * Get avatar resource ID or default if invalid
     * @param resourceId The resource ID to validate
     * @return The validated resource ID or default
     */
    @DrawableRes
    fun getValidAvatarOrDefault(@DrawableRes resourceId: Int): Int {
        return if (avatarList.contains(resourceId)) {
            resourceId
        } else {
            DEFAULT_AVATAR
        }
    }

    /**
     * Get avatar names for display purposes
     */
    val avatarNames = listOf(
        "Blue Avatar",
        "Green Avatar",
        "Purple Avatar",
        "Orange Avatar",
        "Red Avatar",
        "Teal Avatar",
        "Pink Avatar"
    )

    /**
     * Get avatar name by index
     * @param index The index of the avatar
     * @return The display name of the avatar
     */
    fun getAvatarName(index: Int): String {
        return if (index in avatarNames.indices) {
            avatarNames[index]
        } else {
            avatarNames[0]
        }
    }
}