package com.michaelflisar.materialpreferences.core.interfaces

import kotlinx.coroutines.flow.Flow

interface Storage {

    suspend fun clear()

    fun getString(key: String, defaultValue: String): Flow<String>
    suspend fun setString(key: String, value: String)

    fun getBool(key: String, defaultValue: Boolean): Flow<Boolean>
    suspend fun setBool(key: String, value: Boolean)

    fun getInt(key: String, defaultValue: Int): Flow<Int>
    suspend fun setInt(key: String, value: Int)

    fun getFloat(key: String, defaultValue: Float): Flow<Float>
    suspend fun setFloat(key: String, value: Float)

    fun getLong(key: String, defaultValue: Long): Flow<Long>
    suspend fun setLong(key: String, value: Long)

    fun getStringSet(key: String, defaultValue: Set<String>): Flow<Set<String>>
    suspend fun setStringSet(key: String, value: Set<String>)

    fun getIntSet(key: String, defaultValue: Set<Int>): Flow<Set<Int>>
    suspend fun setIntSet(key: String, value: Set<Int>)

    fun getLongSet(key: String, defaultValue: Set<Long>): Flow<Set<Long>>
    suspend fun setLongSet(key: String, value: Set<Long>)
}