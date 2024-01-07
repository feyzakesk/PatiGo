package com.project.patigo.ui.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.project.patigo.data.entity.Pet
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.data.repository.FirebaseAuthRepository
import com.project.patigo.data.repository.FirebaseFirestoreRepository
import com.project.patigo.data.repository.FirebaseStorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertPetFragmentViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository
) :
    ViewModel() {

    val firebaseUser = MutableLiveData<FirebaseUser?>()

    init {
        currentUser()
    }

    fun currentUser() {
        CoroutineScope(Dispatchers.Main).launch {
            firebaseUser.value = firebaseAuthRepository.currentUser()
        }
    }

    fun uploadPicture(bitmap: Bitmap, onComplete:(result:String?)->Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            onComplete(firebaseStorageRepository.getLink(bitmap))
        }
    }

    fun insertPet(
        pet: Pet,
        onComplete: (result: FirebaseFirestoreResult) -> Unit,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            onComplete(firebaseFirestoreRepository.insertPet(pet))
        }
    }
}