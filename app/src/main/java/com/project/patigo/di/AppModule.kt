package com.project.patigo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.project.patigo.data.datasource.FirebaseAuthDataSource
import com.project.patigo.data.datasource.FirebaseFirestoreDataSource
import com.project.patigo.data.datasource.FirebaseStorageDataSource

import com.project.patigo.data.repository.FirebaseAuthRepository
import com.project.patigo.data.repository.FirebaseFirestoreRepository
import com.project.patigo.data.repository.FirebaseStorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(firebaseAuthDataSource: FirebaseAuthDataSource): FirebaseAuthRepository {
        return FirebaseAuthRepository(firebaseAuthDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource {
        return FirebaseAuthDataSource(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestoreRepository(firebaseFirestoreDataSource: FirebaseFirestoreDataSource): FirebaseFirestoreRepository {
        return FirebaseFirestoreRepository(firebaseFirestoreDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDataSource(firebaseFirestoreInstance: FirebaseFirestore): FirebaseFirestoreDataSource {
        return FirebaseFirestoreDataSource(firebaseFirestoreInstance)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageRepository(firebaseStorageDataSource: FirebaseStorageDataSource): FirebaseStorageRepository {
        return FirebaseStorageRepository(firebaseStorageDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageDataSource(firebaseStorageInstance: FirebaseStorage): FirebaseStorageDataSource {
        return FirebaseStorageDataSource(firebaseStorageInstance)
    }

    @Provides
    @Singleton
    fun provideStorageFirestore(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

}


