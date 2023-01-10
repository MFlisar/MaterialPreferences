package com.michaelflisar.materialpreferences.core.interfaces

interface StorageEncryption {

    fun decryptString(data: String): String
    fun encryptString(value: String): String

    fun decryptBool(data: String): Boolean
    fun encryptBool(value: Boolean): String

    fun decryptInt(data: String): Int
    fun encryptInt(value: Int): String

    fun decryptLong(data: String): Long
    fun encryptLong(value: Long): String

    fun decryptFloat(data: String): Float
    fun encryptFloat(value: Float): String

    fun decryptDouble(data: String): Double
    fun encryptDouble(value: Double): String

    fun decryptStringSet(data: String): Set<String>
    fun encryptStringSet(value: Set<String>): String

    fun decryptIntSet(data: String): Set<Int>
    fun encryptIntSet(value: Set<Int>): String

    fun decryptLongSet(data: String): Set<Long>
    fun encryptLongSet(value: Set<Long>): String

    fun decryptFloatSet(data: String): Set<Float>
    fun encryptFloatSet(value: Set<Float>): String

    fun decryptDoubleSet(data: String): Set<Double>
    fun encryptDoubleSet(value: Set<Double>): String
}