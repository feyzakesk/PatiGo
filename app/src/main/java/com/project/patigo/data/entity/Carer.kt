package com.project.patigo.data.entity

import java.io.Serializable

data class Carer(
    var carerId: String,
    var carerName: String,//
    var carerSurname: String,//
    var carerAge: Int?,
    var carerAvailableDay: List<String>,
    var carerIban: String,
    var carerServices: List<String>,//
    var carerProvince: String,//
    var carerReviewList: List<Comment>,
    var carerProfilePict:String,//
    var carerInfo:String,
    var carerPhoneNumber: String
): Serializable{
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "carerId" to carerId,
            "carerName" to carerName,
            "carerSurname" to carerSurname,
            "carerAge" to carerAge,
            "carerAvailableDay" to carerAvailableDay,
            "carerIban" to carerIban,
            "carerServices" to carerServices,
            "carerProvince" to carerProvince,
            "carerReviewList" to carerReviewList,
            "carerProfilePict" to carerProfilePict,
            "carerInfo" to carerInfo,
            "carerPhoneNumber" to carerPhoneNumber
        )
    }

    companion object{
        fun fromMap(map: Map<String, Any?>): Carer {
            return Carer(
                carerId = map["carerId"] as String,
                carerName = map["carerName"] as String,
                carerSurname = map["carerSurname"] as String,
                carerAge = (map["carerAge"] as Long?)?.toInt(),
                carerAvailableDay = map["carerAvailableDay"] as List<String>,
                carerIban = map["carerIban"] as String,
                carerServices = map["carerServices"] as List<String>,
                carerProvince = map["carerProvince"] as String,
                carerReviewList =  (map["carerReviewList"] as List<Map<String, Any>>).map { Comment.fromMap(it) },
                carerProfilePict = map["carerProfilePict"] as String,
                carerInfo = map["carerInfo"] as String,
                carerPhoneNumber = map["carerPhoneNumber"] as String
            )
        }
    }


}
