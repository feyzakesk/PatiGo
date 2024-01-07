package com.project.patigo.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

import com.project.patigo.data.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(val firebaseAuthRepository: FirebaseAuthRepository):ViewModel() {
    val firebaseUser = MutableLiveData<FirebaseUser?>()

    fun currentUser(){
        CoroutineScope(Dispatchers.Main).launch {
            firebaseUser.value=firebaseAuthRepository.currentUser()
        }
    }
}