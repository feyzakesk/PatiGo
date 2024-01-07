package com.project.patigo.data.entity

data class Comment(
    var date: String,
    var userName: String,
    var userSurname: String,
    var vote: Double,
    var comment: String
) {

    companion object {
        fun fromMap(map: Map<String, Any>): Comment {
            return Comment(
                date = map["date"] as String,
                userName = map["userName"] as String,
                userSurname = map["userSurname"] as String,
                vote = map["vote"] as Double,
                comment = map["comment"] as String
            )
        }
    }


    fun toMap(): Map<String, Any> {
        return mapOf(
            "date" to date,
            "userName" to userName,
            "userSurname" to userSurname,
            "vote" to vote,
            "comment" to comment
        )
    }
}