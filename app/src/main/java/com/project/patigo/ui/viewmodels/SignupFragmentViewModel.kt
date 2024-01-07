package com.project.patigo.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.project.patigo.data.entity.User
import com.project.patigo.data.repository.FirebaseFirestoreRepository
import com.project.patigo.data.firebase.FirebaseAuthResult
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.data.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupFragmentViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
) :
    ViewModel() {

    val firebaseUser = MutableLiveData<FirebaseUser?>()
    val exception = MutableLiveData<String?>()


    fun createNewUser(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = firebaseAuthRepository.createUserWithEmailAndPassword(email, password)
            when (result) {
                is FirebaseAuthResult.Success -> {
                    val saveResult=firebaseFirestoreRepository.saveUser(
                        User(
                            result.data.uid, result.data.email!!, "", "", "", "",""
                        )
                    )
                    when (saveResult) {
                        is FirebaseFirestoreResult.Success<*> -> {
                            firebaseUser.value = result.data
                        }

                        is FirebaseFirestoreResult.Failure -> {
                            exception.value = saveResult.error
                        }

                    }

                }

                is FirebaseAuthResult.Failure -> {
                    exception.value = result.error
                }

                else -> {

                }
            }
        }
    }
}