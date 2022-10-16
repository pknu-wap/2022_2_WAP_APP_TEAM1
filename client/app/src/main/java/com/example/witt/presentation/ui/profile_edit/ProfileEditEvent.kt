package com.example.witt.presentation.ui.profile_edit

sealed class ProfileEditEvent {

    object SubmitProfile: ProfileEditEvent()

    data class SubmitProfileImage(val profileImage: String) : ProfileEditEvent()

}