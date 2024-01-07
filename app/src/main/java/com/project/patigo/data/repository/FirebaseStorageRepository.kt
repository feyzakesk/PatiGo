package com.project.patigo.data.repository

import android.graphics.Bitmap
import com.project.patigo.data.datasource.FirebaseStorageDataSource

class FirebaseStorageRepository(private val firebaseStorageDataSource: FirebaseStorageDataSource) {
    suspend fun getLink(
        bitmap: Bitmap,
    ) = firebaseStorageDataSource.getLink(bitmap)
}