package com.example.witt.presentation.ui.profile_edit

import java.io.File

sealed class ProfileEditEvent {

    object SubmitProfile: ProfileEditEvent()

    data class SubmitProfileImage(val profileImage: File) : ProfileEditEvent()

}