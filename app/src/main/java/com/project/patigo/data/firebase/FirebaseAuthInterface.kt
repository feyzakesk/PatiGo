package com.project.patigo.data.firebase

import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthInterface {
    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseAuthResult?
    suspend fun signOut(): Boolean
    suspend fun currentUser(): FirebaseUser?
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        ): FirebaseAuthResult?
}