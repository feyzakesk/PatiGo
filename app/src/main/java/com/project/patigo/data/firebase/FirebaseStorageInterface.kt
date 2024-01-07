package com.project.patigo.data.firebase

import android.graphics.Bitmap

interface FirebaseStorageInterface {
    suspend fun getLink(bitmap: Bitmap): String?
}