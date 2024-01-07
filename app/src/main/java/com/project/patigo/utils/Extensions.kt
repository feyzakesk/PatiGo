package com.project.patigo.utils

import kotlin.random.Random

fun String.getImage(): String = "http://kasimadalan.pe.hu/yemekler/resimler/$this"
fun String.getStar(): String = Constants.starMap[this].toString()
fun String.getLocation(): String = Constants.districtMap[this].toString()
fun String.getDescription(): String = Constants.descriptionMap[this].toString()
fun generateRandomString(length: Int = 8): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random(Random) }
        .joinToString("")
}