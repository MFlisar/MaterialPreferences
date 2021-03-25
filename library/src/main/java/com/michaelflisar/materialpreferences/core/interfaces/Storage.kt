package com.michaelflisar.materialpreferences.core.interfaces

import com.michaelflisar.materialpreferences.core.SettingsChangeEvent
import kotlinx.coroutines.flow.Flow

interface Storage {

    val changeFlow: Flow<SettingsChangeEvent<*>>

    suspend fun clear()

    fun <T : String?> getString(key: String, defaultValue: T): Flow<T>
    suspend fun <T : String?> setString(key: String, value: T)

    fun <T : Boolean?> getBool(key: String, defaultValue: T): Flow<T>
    suspend fun <T : Boolean?> setBool(key: String, value: T)

    fun <T : Int?> getInt(key: String, defaultValue: T): Flow<T>
    suspend fun <T : Int?> setInt(key: String, value: T)

    fun <T : Float?> getFloat(key: String, defaultValue: T): Flow<T>
    suspend fun <T : Float?> setFloat(key: String, value: T)

    fun <T : Double?> getDouble(key: String, defaultValue: T): Flow<T>
    suspend fun <T : Double?> setDouble(key: String, value: T)

    fun <T : Long?> getLong(key: String, defaultValue: T): Flow<T>
    suspend fun <T : Long?> setLong(key: String, value: T)

    fun getStringSet(key: String, defaultValue: Set<String>): Flow<Set<String>>
    suspend fun setStringSet(key: String, value: Set<String>)

    fun getIntSet(key: String, defaultValue: Set<Int>): Flow<Set<Int>>
    suspend fun setIntSet(key: String, value: Set<Int>)

    fun getLongSet(key: String, defaultValue: Set<Long>): Flow<Set<Long>>
    suspend fun setLongSet(key: String, value: Set<Long>)

    suspend fun <T : Any?> onValueChanged(setting: StorageSetting<T>, value: T)
}