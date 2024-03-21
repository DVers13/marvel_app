package com.example.marvel.util

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constants {
    companion object{
        const val BASE_URL = "https://gateway.marvel.com"
        val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()

        const val API_KEY = "9d2c1ca0b8fbd53fa69875dde6baa731"
        const val PRIVATE_KEY = "c0648e47a3b120b8d2e638ebb35a765142feb180"
        const val limit = "5"
        fun hash(): String{
            val input = "$timeStamp$PRIVATE_KEY$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}