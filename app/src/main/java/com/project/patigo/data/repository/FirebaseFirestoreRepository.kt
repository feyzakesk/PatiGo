package com.project.patigo.data.repository

import com.project.patigo.data.datasource.FirebaseFirestoreDataSource
import com.project.patigo.data.entity.Carer
import com.project.patigo.data.entity.Pet
import com.project.patigo.data.entity.User

class FirebaseFirestoreRepository(private val firebaseFirestoreDataSource: FirebaseFirestoreDataSource) {
    suspend fun getUserById(
        userId: String,
    ) = firebaseFirestoreDataSource.getUserById(userId)


    suspend fun saveUser(
        user: User,
    ) = firebaseFirestoreDataSource.saveUser(user)

    suspend fun getCarers(
    ) = firebaseFirestoreDataSource.getCarers()

    suspend fun saveCarer(
        carer: Carer,
    ) = firebaseFirestoreDataSource.saveCarer(carer)

    suspend fun updateUser(
        userId: String,
        map: Map<String, Any>,
    ) = firebaseFirestoreDataSource.updateUser(userId, map)

    suspend fun insertPet(
        pet: Pet,
    ) = firebaseFirestoreDataSource.insertPet(pet)

    suspend fun getPets(
        userId: String,
    ) = firebaseFirestoreDataSource.getPets(userId)

    suspend fun deletePet(
        userId: String,
        petId: String,
    ) = firebaseFirestoreDataSource.deletePet(userId, petId)


}