package com.project.patigo.data.entity

data class User(
    var userId: String,
    var userEmail: String,
    var userName: String,
    var userSurname: String,
    var userPhoneNumber: String,
    var userAddress: String,
    var userPicture: String,
) {
    companion object {
        fun fromMap(map: Map<String, Any>): User {
            return User(
                userId = map["userId"] as String,
                userName = map["userName"] as String,
                userSurname = map["userSurname"] as String,
                userPhoneNumber = map["userPhoneNumber"] as String,
                userAddress = map["userAddress"] as String,
                userPicture = map["userPicture"] as String,
                userEmail = map["userEmail"] as String
            )
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "userId" to userId,
            "userName" to userName,
            "userSurname" to userSurname,
            "userPhoneNumber" to userPhoneNumber,
            "userAddress" to userAddress,
            "userPicture" to userPicture,
            "userEmail" to userEmail

        )
    }
}
