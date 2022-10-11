package com.example.witt.presentation.ui.profile_edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileEditViewModel: ViewModel() {

    val inputName : MutableLiveData<String> = MutableLiveData()
    val inputPhoneNumber : MutableLiveData<String> = MutableLiveData()


}