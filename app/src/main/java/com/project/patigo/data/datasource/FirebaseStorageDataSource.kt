package com.project.patigo.data.datasource

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.project.patigo.data.entity.User
import com.project.patigo.data.firebase.FirebaseStorageInterface
import com.project.patigo.utils.generateRandomString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class FirebaseStorageDataSource(private val firebaseStorageInstance: FirebaseStorage) :
    FirebaseStorageInterface {

    companion object {
        var error = ""
    }

    //Içeri resim yolluyorum-> bunu kaydediyor-> Link veriyor
    //Suspend-> Askıda demek
    // a=5 b=10 a+b // sync
    // Coroutines -> Kotlinde async işlemleri yönetmek için bir metot(yöntem)...
    // Dispatchers ->
    // IO-> İnternete çıktığın zaman kullanman gereken
    // Default-> Görüntü işleme gibi ağır cpu gerektiren işlemler
    // Main-> Arayüzü etkileyen olaylar burada gerçekleşiyor....
    override suspend fun getLink(bitmap: Bitmap): String? = withContext(Dispatchers.IO) {
        return@withContext try {
            val randomId = generateRandomString()
            val storageRef =
                firebaseStorageInstance.reference.child("images/$randomId.png")
            val imageData = convertBitmapToByteArray(bitmap)
            storageRef.putBytes(imageData).await()
            val uri=storageRef.downloadUrl.await()
            Log.e("TAG",uri.toString())
            uri.toString()
        } catch (exception: Exception) {
            error = exception.message.toString()
            null
        }
    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

}