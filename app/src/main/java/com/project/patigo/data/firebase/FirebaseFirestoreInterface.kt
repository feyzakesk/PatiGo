package com.project.patigo.data.firebase

import com.project.patigo.data.entity.Carer
import com.project.patigo.data.entity.Pet
import com.project.patigo.data.entity.User

interface FirebaseFirestoreInterface {
    suspend fun getUserById(userId: String): FirebaseFirestoreResult
    suspend fun updateUser(userId: String, map: Map<String, Any>): FirebaseFirestoreResult
    suspend fun saveUser(user: User): FirebaseFirestoreResult
    suspend fun insertPet(pet: Pet):FirebaseFirestoreResult
    suspend fun getPets(userId: String):FirebaseFirestoreResult
    suspend fun deletePet(userId: String,petId:String): FirebaseFirestoreResult
    suspend fun saveCarer(carer: Carer): FirebaseFirestoreResult
    suspend fun getCarers(): FirebaseFirestoreResult
}